package br.vet.certvet.services.implementation;

import br.vet.certvet.exceptions.*;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.ProntuarioService;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import br.vet.certvet.models.*;
import br.vet.certvet.repositories.*;
import br.vet.certvet.services.PdfService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProntuarioServiceImpl implements ProntuarioService {
    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private CirurgiaRepository cirurgiaRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private DocumentoService documentoService;

    private String getProntuarioName(Prontuario prontuario){
        return prontuario.getCodigo() + ".pdf";
    }

    @Override
    public Optional<Prontuario> findById(Long id) {
        return prontuarioRepository.findById(id);
    }

    @Override
    public byte[] retrievePdfFromRepository(Prontuario prontuario) throws IOException {
        return pdfRepository.retrieveObject(
                prontuario.getClinica().getCnpj(),
                getProntuarioName(prontuario)
        );
    }

    @Override
    public Prontuario save(Prontuario prontuario) {
        log.info("prontuario: " + prontuario);
        Date now = new Date();
        String codigo = Prontuario.createCodigo(LocalDateTime.now());
//        return prontuarioRepository.save(prontuario);
        prontuario.setCodigo(codigo);
        Optional<Clinica> clinica = clinicaRepository.findById(prontuario.getClinica().getId());
        if(clinica.isEmpty()) throw new ClinicaNotFoundException("Clínica não cadastrada ou não identificada");
        Optional<Usuario> tutor = tutorRepository.findById(prontuario.getTutor().getId());
        if(tutor.isEmpty()) throw new TutorNotFoundException("Tutor não cadastrado ou não identificado");
        Optional<Animal> animal = animalRepository.findByTutores_idAndNome(tutor.get().getId(), prontuario.getAnimal().getNome());
        if(animal.isEmpty()) throw new AnimalNotFoundException("Animal não cadastrado ou não identificado");
        prontuario.setClinica(clinica.get());

        prontuario.setTutor(tutor.get());

        prontuario.setAnimal(animal.get());

        log.debug("Iniciando persistência do prontuário");
        Documento doc = Documento.builder()
                .codigo(codigo)
                .versao(1)
                .criadoEm(now)
                .veterinario(prontuario.getVeterinario())
                .clinica(clinica.get())
                .caminhoArquivo("/" + S3BucketServiceRepository.getConventionedBucketName(prontuario.getClinica().getCnpj()) + "/" + prontuario.getCodigo()+".pdf")
                .build();
//        log.debug("doc a ser persistido: " + doc);
        Documento tempDoc = documentoRepository.save(doc);
        log.debug("doc persistido");
        Prontuario p = prontuario.setDocumentoDetails(tempDoc);
        log.debug("Prontuario atualizado");
        p = prontuarioRepository.save(p);
        log.debug("prontuario persistido");
        documentoRepository.save(tempDoc.setProntuario(p));
        log.debug("documento persistido");
        try {
            pdfService.writeProntuario(p);
            log.debug("Processo de gravação de PDF finalizado");
        } catch (SQLException e){
            log.error("\"Erro de validação SQL do ID da Clínica\": " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    @Override
    public boolean exists(LocalDateTime dataAtendimento) {
        return prontuarioRepository.existsByDataAtendimento(dataAtendimento);
    }

    @Override
    public Optional<Prontuario> findByCodigo(String certvetCode) {
        return Optional.empty();
    }

    @Override
    public Optional<Prontuario> getByCodigo(String codigo) {
        return prontuarioRepository.findByCodigo(codigo);
    }
    @Override
    public Optional<Prontuario> createProntuario(Prontuario prontuario) {
        return Optional.empty();
    }

    @Override
    public Optional<Prontuario> editProntuario(Prontuario prontuario) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteProntuario(Prontuario prontuario) {
        return Boolean.FALSE;
    }

    @Override
    public byte[] getProntuarioPdfBy(Long id) {
        return new byte[0];
    }

    @Override
    public byte[] getProntuarioPdfBy(Prontuario prontuario) {
        return new byte[0];
    }

    @Override
    public List<Documento> getDocumentosFromProntuarioByTipo(String prontuarioCodigo, String tipo) {
        return prontuarioRepository.findAllByCodigoAndDocumentos_tipo(prontuarioCodigo, tipo);
    }

    @Override
    public Prontuario attachDocumentoAndPdfPersist(
            String prontuarioCodigo,
            String documentoCodigo,
            byte[] documentoBinary
    ) throws ProntuarioNotFoundException,
            DocumentoNotFoundException,
            SQLException,
            OptimisticLockingFailureException {
        Prontuario prontuario = prontuarioRepository.findByCodigo(prontuarioCodigo)
                .orElseThrow(ProntuarioNotFoundException::new);
        final String fileName = prontuario.getCodigo() + ".pdf";
        Documento documento = prontuario.getDocumentos()
                .stream()
                .filter(doc -> documentoCodigo.equals(doc.getCodigo()))
                .findFirst()
                .orElseThrow(DocumentoNotFoundException::new);

        log.info("Iniciando persistência no serviço AWS S3");
        var res = persistObjectInAws(prontuario, fileName, documentoBinary);
        if(null == res)
            throw new DocumentoNotPersistedException("Não foi possível gerar o documento com sucesso.");
        try{
            documentoRepository.save(setDocumentoMetadata(prontuario, documento, res));
        } catch (OptimisticLockingFailureException e){
            log.error("Documento não salvo");
            throw e;
        }
        prontuario = prontuarioRepository.save(prontuario);
        log.info("Prontuário Salvo");
        return prontuario;
    }

    private PutObjectResult persistObjectInAws(Prontuario prontuario, String fileName, byte[] pdf) throws SQLException {
        return pdfRepository.putObject(
                clinicaRepository.findById(
                                prontuario.getClinica().getId()
                        ).stream()
                        .findFirst()
                        .orElseThrow(SQLException::new)
                        .getCnpj(),
                fileName.substring(fileName.indexOf("/")+1),
                pdf
        );
    }

    private Documento setDocumentoMetadata(Prontuario prontuario, Documento documentoTipo, PutObjectResult res) {
        return documentoTipo
                .setMd5(res.getContentMd5())
                .setEtag(res.getETag())
                .setAlgorithm(res.getSSEAlgorithm())
                .setProntuario(prontuario);
    }
}

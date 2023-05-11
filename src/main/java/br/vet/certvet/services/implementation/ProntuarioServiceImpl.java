package br.vet.certvet.services.implementation;

import br.vet.certvet.exceptions.*;
import br.vet.certvet.models.Documento;
import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.models.factories.ProntuarioFactory;
import br.vet.certvet.models.mappers.ProntuarioDTOMapper;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.ProntuarioService;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDateTime;
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
    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Override
    public Optional<byte[]> retrievePdfFromRepository(Prontuario prontuario) throws IOException {
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
        documentoRepository.save(tempDoc.prontuario(p));
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
    public Optional<Prontuario> findByCodigo(String codigo) {
        return prontuarioRepository.findByCodigo(codigo);
    }
    @Override
    public Prontuario create(ProntuarioDTO dto, Animal animal, Usuario tutor, Usuario veterinario) {
        Prontuario prontuario = ProntuarioFactory.factory(dto)
                .setCodigo(LocalDateTime.now())
                .setAnimal(animal)
                .setTutor(tutor)
                .setVeterinario(veterinario);

        return this.prontuarioRepository.saveAndFlush(prontuario);
    }

    @Override
    public Prontuario edit(ProntuarioDTO dto, Prontuario prontuario) {
        ProntuarioDTOMapper.mapper(dto, prontuario);

        return this.prontuarioRepository.saveAndFlush(prontuario);
    }

    @Override
    public Prontuario findOne(Long id, Animal animal) {
        Optional<Prontuario> response = this.prontuarioRepository.findOneByIdAndAnimal(id, animal);

        if (response.isEmpty())
            throw new NotFoundException("Prontuário não encontrado");

        return response.get();
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
    public Documento attachDocumentoAndPdfPersist(
            Documento documento,
            ObjectMetadata awsResponse
    ) throws ProntuarioNotFoundException,
            DocumentoNotFoundException,
            OptimisticLockingFailureException {
        final String fileName = writeNomeArquivo(documento);
        log.info("Iniciando persistência no serviço AWS S3");

        if(awsResponse.getETag() == null)
            throw new DocumentoNotPersistedException("Não foi possível gerar o documento com sucesso.");
        try {
            log.debug("Persistindo atualização dos documentos");
            return documentoRepository.saveAndFlush(setDocumentoMetadata(documento, awsResponse, fileName));
        } catch (OptimisticLockingFailureException e){
            log.error("Documento não salvo");
            throw e;
        } finally {
            log.info("Documento Salvo: " + fileName);
        }
    }

    public static String writeNomeArquivo(Documento documento) {
        return new StringBuilder()
                .append(documento.getProntuario().getCodigo())
                .append("-doc-")
                .append(documento.getTipo())
                .append("-")
                .append(documento.getCodigo())
                .append(".pdf")
                .toString();
    }

    private Documento setDocumentoMetadata(Documento documentoTipo, ObjectMetadata res, String fileName) {
        return documentoTipo
                .md5(res.getContentMD5())
                .etag(res.getETag())
                .algorithm(res.getSSEAlgorithm())
                .caminhoArquivo(fileName)
//                .setProntuario(prontuario)
                ;
    }
}

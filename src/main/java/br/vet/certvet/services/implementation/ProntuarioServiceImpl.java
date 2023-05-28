package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.responses.*;
import br.vet.certvet.enums.ProntuarioStatus;
import br.vet.certvet.exceptions.*;
import br.vet.certvet.models.*;
import br.vet.certvet.repositories.*;
import br.vet.certvet.services.*;
import org.springframework.data.domain.*;
import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.models.factories.ProntuarioFactory;
import br.vet.certvet.models.mappers.ProntuarioDTOMapper;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private EstoqueService estoqueService;

    private static final int RESPONSE_LIMIT = 30;

    private String getProntuarioName(Prontuario prontuario) {
        return prontuario.getCodigo() + ".pdf";
    }

    @Override
    public Optional<Prontuario> findById(Long id) {
        return prontuarioRepository.findById(id);
    }

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
        if (clinica.isEmpty()) throw new ClinicaNotFoundException("Clínica não cadastrada ou não identificada");
        Optional<Usuario> tutor = tutorRepository.findById(prontuario.getTutor().getId());
        if (tutor.isEmpty()) throw new TutorNotFoundException("Tutor não cadastrado ou não identificado");
        Optional<Animal> animal = animalRepository.findByTutores_idAndNome(tutor.get().getId(), prontuario.getAnimal().getNome());
        if (animal.isEmpty()) throw new AnimalNotFoundException("Animal não cadastrado ou não identificado");
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
                .caminhoArquivo("/" + S3BucketServiceRepository.getConventionedBucketName(prontuario.getClinica().getCnpj()) + "/" + prontuario.getCodigo() + ".pdf")
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
        } catch (SQLException e) {
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
        ProntuarioDTOMapper.assignToModel(dto, prontuario);

        return this.prontuarioRepository.saveAndFlush(prontuario);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Prontuario finalizeMedicalRecord(Prontuario prontuario) {
        int version = prontuario.getVersao();

        if (prontuario.getStatus() != ProntuarioStatus.PENDING) {
            return prontuario;
        }

        Cirurgia cirurgia = prontuario.getCirurgia();
        List<Procedimento> procedimentos = prontuario.getProcedimentos();

        if (cirurgia != null)
            this.handleCirurgia(cirurgia, prontuario);

        procedimentos.forEach((procedimento) -> this.handleProcedimento(procedimento, prontuario));

        prontuario.setDataAtendimento(LocalDateTime.now()).setVersao(++version).setStatus(ProntuarioStatus.COMPLETED);

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
    public PaginatedResponse<ProntuarioResponseDTO> findAll(int page, String search, String url, Animal animal) {
        page = Math.max(page, 1);

        Pageable pageable = PageRequest.of(page - 1, ProntuarioServiceImpl.RESPONSE_LIMIT);

        Long total = search.trim().isEmpty() ?
                this.prontuarioRepository.countByAnimal(animal) :
                this.prontuarioRepository.countByAnimalAndCodigoContains(animal, search);

        Metadata metadata = new Metadata(url, page, ProntuarioServiceImpl.RESPONSE_LIMIT, total);

        List<Prontuario> prontuarios = search.trim().isEmpty() ?
                this.prontuarioRepository.findAllByAnimal(pageable, animal) :
                this.prontuarioRepository.findAllByAnimalAndCodigoContains(pageable, animal, search);

        List<ProntuarioResponseDTO> medicamentoResponseDtos = prontuarios.stream()
                .map(ProntuarioResponseDTO::new)
                .toList();

        return new PaginatedResponse<>(metadata, medicamentoResponseDtos);
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

        if (awsResponse.getETag() == null)
            throw new DocumentoNotPersistedException("Não foi possível gerar o documento com sucesso.");
        try {
            log.debug("Persistindo atualização dos documentos");
            return documentoRepository.saveAndFlush(setDocumentoMetadata(documento, awsResponse, fileName));
        } catch (OptimisticLockingFailureException e) {
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

    private void handleCirurgia(Cirurgia cirurgia, Prontuario prontuario) {
        cirurgia.getMedicamentosConsumidos().forEach((cirurgiaEstoqueMedicamento) -> {
            final BigDecimal dose = cirurgiaEstoqueMedicamento.getDose();
            final Usuario veterinario = prontuario.getVeterinario();
            final Estoque estoque = cirurgiaEstoqueMedicamento.getEstoque();
            final String reason = new StringBuilder("Usado na cirurgia ")
                    .append(cirurgia.getDescricao())
                    .append(" no prontuário ")
                    .append(prontuario.getCodigo())
                    .append(" do animal ")
                    .append(prontuario.getAnimal().getNome()).toString();

            this.estoqueService.subtract(dose, reason, estoque, veterinario);
        });
    }

    private void handleProcedimento(Procedimento procedimento, Prontuario prontuario) {
        final BigDecimal dose = procedimento.getDoseMedicamento();
        final Usuario veterinario = prontuario.getVeterinario();
        final Estoque estoque = procedimento.getMedicamentoConsumido();

        if (dose == null || estoque == null)
            return;

        final String reason = new StringBuilder("Usado no procedimento ")
                .append(procedimento.getDescricao())
                .append(" no prontuário ")
                .append(prontuario.getCodigo())
                .append(" do animal ")
                .append(prontuario.getAnimal().getNome()).toString();

        this.estoqueService.subtract(dose, reason, estoque, veterinario);
    }
}

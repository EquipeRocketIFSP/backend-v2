package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.prontuario.ManifestacoesClinicasDTO;
import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.dto.responses.Metadata;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.dto.responses.ProntuarioResponseDTO;
import br.vet.certvet.enums.ProntuarioStatus;
import br.vet.certvet.exceptions.*;
import br.vet.certvet.models.*;
import br.vet.certvet.models.factories.ProntuarioFactory;
import br.vet.certvet.models.mappers.ProntuarioDTOMapper;
import br.vet.certvet.repositories.*;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.EstoqueService;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProntuarioServiceImpl implements ProntuarioService {
    private final ProntuarioRepository prontuarioRepository;

    private final PdfRepository pdfRepository;

    private final CirurgiaRepository cirurgiaRepository;

    private final TutorRepository tutorRepository;

    private final DocumentoRepository documentoRepository;

    private final PdfService pdfService;

    private final ClinicaRepository clinicaRepository;

    private final AnimalRepository animalRepository;

    private final DocumentoService documentoService;

    @Autowired
    private ApetiteRepository apetiteRepository;

    @Autowired
    private LinfonodoRepository linfonodoRepository;

    @Autowired
    private MusculoRepository musculoRepository;

    @Autowired
    private AbdomenRegioesRepository abdomenRegioesRepository;

    @Autowired
    private ColunaRegioesRepository colunaRegioesRepository;

    public ProntuarioServiceImpl(ProntuarioRepository prontuarioRepository, PdfRepository pdfRepository, CirurgiaRepository cirurgiaRepository, TutorRepository tutorRepository, DocumentoRepository documentoRepository, PdfService pdfService, ClinicaRepository clinicaRepository, AnimalRepository animalRepository, DocumentoService documentoService) {
        this.prontuarioRepository = prontuarioRepository;
        this.pdfRepository = pdfRepository;
        this.cirurgiaRepository = cirurgiaRepository;
        this.tutorRepository = tutorRepository;
        this.documentoRepository = documentoRepository;
        this.pdfService = pdfService;
        this.clinicaRepository = clinicaRepository;
        this.animalRepository = animalRepository;
        this.documentoService = documentoService;
    }

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
        if (null != prontuario.getCodigo())
            return prontuarioRepository.saveAndFlush(prontuario);
        Optional<Clinica> clinica = clinicaRepository.findById(prontuario.getClinica().getId());
        if (clinica.isEmpty()) throw new ClinicaNotFoundException("Clínica não cadastrada ou não identificada");
        Optional<Usuario> tutor = tutorRepository.findById(prontuario.getTutor().getId());
        if (tutor.isEmpty()) throw new TutorNotFoundException("Tutor não cadastrado ou não identificado");
        Optional<Animal> animal = animalRepository.findByTutoridAndNome(tutor.get().getId(), prontuario.getAnimal().getNome());
        if (animal.isEmpty()) throw new AnimalNotFoundException("Animal não cadastrado ou não identificado");
        log.debug("Iniciando persistência do prontuário");
        return prontuarioRepository.saveAndFlush(
                prontuario.setClinica(clinica.get())
                        .setTutor(tutor.get())
                        .setAnimal(animal.get())
                        .setCodigo(Prontuario.createCodigo(LocalDateTime.now()))
        );
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
                .setClinica(veterinario.getClinica())
                .setAnimal(animal)
                .setTutor(tutor)
                .setVeterinario(veterinario);

        return this.prontuarioRepository.saveAndFlush(prontuario);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Prontuario edit(ProntuarioDTO dto, Prontuario prontuario) {
        ProntuarioDTOMapper.assignToModel(dto, prontuario);

        if (dto instanceof ManifestacoesClinicasDTO) {
            prontuario.getColunaRegioes().clear();
            prontuario.getAbdomenRegioes().clear();
            prontuario.getMusculos().clear();
            prontuario.getLinfonodos().clear();

            List<ApetiteModel> apetiteModelList = this.apetiteRepository.findAll();
            List<Linfonodo> linfonodos = this.linfonodoRepository.findAll();
            List<Musculo> musculos = this.musculoRepository.findAll();
            List<AbdomenRegioes> abdomenRegioes = this.abdomenRegioesRepository.findAll();
            List<ColunaRegioes> colunaRegioes = this.colunaRegioesRepository.findAll();

            List<Linfonodo> selectedLinfonodos = linfonodos
                    .stream()
                    .filter(model -> model.getLinfonodo().equals(((ManifestacoesClinicasDTO) dto).getLinfonodos()))
                    .toList();

            Optional<ApetiteModel> apetite = apetiteModelList
                    .stream()
                    .filter(
                            apetiteModel -> apetiteModel
                                    .getStatus().getStatus()
                                    .equals(((ManifestacoesClinicasDTO) dto)
                                            .getApetite()
                                            .toLowerCase()))
                    .findFirst();


            List<String> colunas = ((ManifestacoesClinicasDTO) dto).getColuna();
            List<String> abdomen = ((ManifestacoesClinicasDTO) dto).getAbdomen();
            List<String> mPelvicos = ((ManifestacoesClinicasDTO) dto).getMPelvicos().stream().map((str) -> "Pélvicos " + str).toList();
            List<String> mToracicos = ((ManifestacoesClinicasDTO) dto).getMToracicos().stream().map((str) -> "Torácico " + str).toList();

            List<ColunaRegioes> selectedColunas = colunaRegioes
                    .stream()
                    .filter(model -> colunas.contains(model.getNome()))
                    .toList();

            List<AbdomenRegioes> selectedAbdomens = abdomenRegioes
                    .stream()
                    .filter(model -> abdomen.contains(model.getNome()))
                    .toList();

            List<Musculo> selectedMusculos = new ArrayList<>();

            selectedMusculos.addAll(
                    musculos.stream()
                            .filter(model -> mPelvicos.contains(model.getNome()))
                            .toList());

            selectedMusculos.addAll(
                    musculos.stream()
                            .filter(model -> mToracicos.contains(model.getNome()))
                            .toList());

            apetite.ifPresent(apetiteModel -> prontuario.getManifestacoesClinicas().setApetite(apetiteModel));

            prontuario.getColunaRegioes().addAll(selectedColunas);
            prontuario.getAbdomenRegioes().addAll(selectedAbdomens);
            prontuario.getMusculos().addAll(selectedMusculos);
            prontuario.getLinfonodos().addAll(selectedLinfonodos);
        }

        if (prontuario.getStatus() == ProntuarioStatus.COMPLETED)
            prontuario.setStatus(ProntuarioStatus.UPDATING);

        return this.prontuarioRepository.saveAndFlush(prontuario);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Prontuario finalizeMedicalRecord(Prontuario prontuario) {
        final ProntuarioStatus status = prontuario.getStatus();

        if (status == ProntuarioStatus.COMPLETED)
            return prontuario;
        else if (status == ProntuarioStatus.PENDING)
            prontuario.setDataAtendimento(LocalDateTime.now());

        prontuario.setStatus(ProntuarioStatus.COMPLETED);

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
        return prontuarioRepository.findAllByCodigoAndDocumentoTipo(prontuarioCodigo, tipo);
    }

    @Override
    public Documento attachDocumentoAndPdfPersist(
            final Documento documento,
            final ObjectMetadata awsResponse,
            final int version
    ) throws ProntuarioNotFoundException,
            DocumentoNotFoundException,
            OptimisticLockingFailureException {
        final String fileName = writeNomeArquivo(documento, version);
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

    public static String writeNomeArquivo(Documento documento, int version) {
        return new StringBuilder()
                .append(documento.getProntuario().getCodigo())
                .append("-doc-")
                .append(documento.getTipo())
                .append("-")
                .append(documento.getCodigo())
                .append("-v")
                .append(version)
                .append(".pdf")
                .toString();
    }

    public static String writeNomeArquivoPrescricao(Prontuario prontuario, int version) {
        return new StringBuilder()
                .append(prontuario.getCodigo())
                .append("-pres-")
                .append("v")
                .append(version)
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

package br.vet.certvet.dto.responses;

import br.vet.certvet.enums.ProntuarioStatus;
import br.vet.certvet.models.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ProntuarioResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("frequencia_cardiaca")
    private int frequenciaCardiaca;

    @JsonProperty("frequencia_respiratoria")
    private int frequenciaRespiratoria;

    @JsonProperty("temperatura")
    private int temperatura;

    @JsonProperty("peso")
    private String peso;

    @JsonProperty("hidratacao")
    private String hidratacao;

    @JsonProperty("tpc")
    private String tpc;

    @JsonProperty("mucosa")
    private String mucosa;

    @JsonProperty("conciencia")
    private String conciencia;

    @JsonProperty("escore_corporal")
    private String escoreCorporal;

    @JsonProperty("supeita_diagnostica")
    private String supeitaDiagnostica;

    @JsonProperty("prostracao")
    private boolean prostracao;

    @JsonProperty("febre")
    private boolean febre;

    @JsonProperty("vomito")
    private boolean vomito;

    @JsonProperty("diarreia")
    private boolean diarreia;

    @JsonProperty("espasmos_convulsao")
    private boolean espasmosConvulsao;

    @JsonProperty("deambulacao")
    private boolean deambulacao;

    @JsonProperty("sensibilidade_dor")
    private boolean sensibilidadeDor;

    @JsonProperty("lesoes_nodulos")
    private boolean lesoesNodulos;

    @JsonProperty("apetite")
    private String apetite;

    @JsonProperty("linfonodos")
    private String linfonodos;

    @JsonProperty("linfonodos_obs")
    private String linfonodosObs;

    @JsonProperty("coluna")
    private Object[] regiaoColuna = {};

    @JsonProperty("abdomen")
    private Object[] regiaoAbdomen = {};

    @JsonProperty("m_toracicos")
    private Object[] regiaoMToracicos = {};

    @JsonProperty("m_pelvicos")
    private Object[] regiaoMPelvicos = {};

    @JsonProperty("regioes_obs")
    private String regioesObs;

    @JsonProperty("cabeca")
    private boolean regiaoCabeca;

    @JsonProperty("torax")
    private boolean regiaoTorax;

    @JsonProperty("exames")
    private List<ExameResponseDTO> exames;

    @JsonProperty("procedimentos")
    private List<ProcedimentoResponseDTO> procedimentos;

    @JsonProperty("cirurgia")
    private CirurgiaResponseDTO cirurgia;

    @JsonProperty("data_atendimento")
    private String dataAtendimento;

    @JsonProperty("codigo")
    private String codigo;

    @JsonProperty("versao")
    private Integer versao;

    @JsonProperty("status")
    private ProntuarioStatus status;

    @JsonProperty("animal")
    private AnimalResponseDto animal;

    @JsonProperty("tutor")
    private UsuarioResponseDto tutor;

    @JsonProperty("veterinario")
    private UsuarioResponseDto veterinario;

    @JsonProperty("prescricoes")
    private List<PrescricaoResponseDTO> prescricoes = new ArrayList<>();

    public ProntuarioResponseDTO(Prontuario prontuario) {
        this.id = prontuario.getId();
        this.frequenciaRespiratoria = prontuario.getFrequenciaRespiratoria();
        this.frequenciaCardiaca = prontuario.getFrequenciaCardiaca();
        this.temperatura = prontuario.getTemperatura();
        this.peso = prontuario.getPeso();
        this.hidratacao = prontuario.getHidratacao();
        this.tpc = prontuario.getTpc();
        this.mucosa = prontuario.getMucosa();
        this.conciencia = prontuario.getConciencia();
        this.escoreCorporal = prontuario.getEscoreCorporal();
        this.supeitaDiagnostica = prontuario.getSupeitaDiagnostica();
        this.regiaoColuna = prontuario.getColunaRegioes().stream().map(ColunaRegioes::getNome).toArray();
        this.regiaoAbdomen = prontuario.getAbdomenRegioes().stream().map(AbdomenRegioes::getNome).toArray();
        this.regiaoMToracicos = prontuario
                .getMusculos()
                .stream()
                .filter(musculo -> musculo.getNome().matches("(.*)Torácico(.*)"))
                .map(musculo -> musculo.getNome().replace("Torácico ", ""))
                .toArray();

        this.regiaoMPelvicos = prontuario
                .getMusculos()
                .stream()
                .filter(musculo -> musculo.getNome().matches("(.*)Pélvicos(.*)"))
                .map(musculo -> musculo.getNome().replace("Pélvicos ", ""))
                .toArray();

        final ManifestacoesClinicas manifestacoesClinicas = prontuario.getManifestacoesClinicas();

        if (manifestacoesClinicas != null) {
            this.prostracao = manifestacoesClinicas.isProstracao();
            this.febre = manifestacoesClinicas.isFebre();
            this.vomito = manifestacoesClinicas.isVomito();
            this.diarreia = manifestacoesClinicas.isDiarreia();
            this.espasmosConvulsao = manifestacoesClinicas.isEspasmosConvulsao();
            this.deambulacao = manifestacoesClinicas.isDeambulacao();
            this.sensibilidadeDor = manifestacoesClinicas.isSensibilidadeDor();
            this.lesoesNodulos = manifestacoesClinicas.isLesoesNodulos();
            this.apetite = manifestacoesClinicas.getApetite().getStatus().getStatus();
            this.linfonodos = !prontuario.getLinfonodos().isEmpty() ? prontuario.getLinfonodos().get(0).getLinfonodo() : null;
            //this.linfonodosObs = manifestacoesClinicas.getLinfonodosObs();
            this.regiaoCabeca = manifestacoesClinicas.isRegiaoCabeca();
            this.regiaoTorax = manifestacoesClinicas.isRegiaoTorax();
            this.regioesObs = manifestacoesClinicas.getRegioesObs();
        }

        this.codigo = prontuario.getCodigo();
        this.animal = AnimalResponseDto.of(prontuario.getAnimal());
        this.tutor = new UsuarioResponseDto(prontuario.getTutor());
        this.veterinario = new UsuarioResponseDto(prontuario.getVeterinario());
        this.exames = prontuario.getExames().stream().map(ExameResponseDTO::new).toList();
        this.procedimentos = prontuario.getProcedimentos().stream().map(ProcedimentoResponseDTO::new).toList();
        this.versao = prontuario.getVersao();
        this.status = prontuario.getStatus();

        if (prontuario.getPrescricoes() != null)
            this.prescricoes = prontuario.getPrescricoes()
                    .stream()
                    .filter(prescricao -> prescricao.getDataExclusao() == null)
                    .map(PrescricaoResponseDTO::new).toList();

        if (prontuario.getCirurgia() != null)
            this.cirurgia = new CirurgiaResponseDTO(prontuario.getCirurgia());

        if (prontuario.getDataAtendimento() != null)
            this.dataAtendimento = prontuario.getDataAtendimento().toString();
        else
            this.dataAtendimento = null;
    }
}

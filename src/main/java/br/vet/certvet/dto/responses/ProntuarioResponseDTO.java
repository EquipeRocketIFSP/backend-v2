package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Prontuario;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String[] regiaoColuna = {};

    @JsonProperty("abdomen")
    private String[] regiaoAbdomen = {};

    @JsonProperty("m_toracicos")
    private String[] regiaoMToracicos = {};

    @JsonProperty("m_pelvicos")
    private String[] regiaoMPelvicos = {};

    @JsonProperty("regioes_obs")
    private String regioesObs;

    @JsonProperty("cabeca")
    private boolean regiaoCabeca;

    @JsonProperty("torax")
    private boolean regiaoTorax;

    @JsonProperty("exames")
    private List<ExameResponseDTO> exames;

    @JsonProperty("data_atendimento")
    private String dataAtendimento;

    @JsonProperty("codigo")
    private String codigo;

    @JsonProperty("animal")
    private AnimalResponseDto animal;

    @JsonProperty("tutor")
    private UsuarioResponseDto tutor;

    @JsonProperty("veterinario")
    private UsuarioResponseDto veterinario;

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
        this.prostracao = prontuario.isProstracao();
        this.febre = prontuario.isFebre();
        this.vomito = prontuario.isVomito();
        this.diarreia = prontuario.isDiarreia();
        this.espasmosConvulsao = prontuario.isEspasmosConvulsao();
        this.deambulacao = prontuario.isDeambulacao();
        this.sensibilidadeDor = prontuario.isSensibilidadeDor();
        this.lesoesNodulos = prontuario.isLesoesNodulos();
        this.apetite = prontuario.getApetite();
        this.linfonodos = prontuario.getLinfonodos();
        this.linfonodosObs = prontuario.getLinfonodosObs();
        this.regiaoCabeca = prontuario.isRegiaoCabeca();
        this.regiaoTorax = prontuario.isRegiaoTorax();
        this.regioesObs = prontuario.getRegioesObs();
        this.codigo = prontuario.getCodigo();
        this.animal = new AnimalResponseDto(prontuario.getAnimal());
        this.tutor = new UsuarioResponseDto(prontuario.getTutor());
        this.veterinario = new UsuarioResponseDto(prontuario.getVeterinario());
        this.exames = prontuario.getExames().stream().map(ExameResponseDTO::new).toList();

        if (prontuario.getRegiaoColuna() != null)
            this.regiaoColuna = prontuario.getRegiaoColuna().split(";");

        if (prontuario.getRegiaoAbdomen() != null)
            this.regiaoAbdomen = prontuario.getRegiaoAbdomen().split(";");

        if (prontuario.getRegiaoMToracicos() != null)
            this.regiaoMToracicos = prontuario.getRegiaoMToracicos().split(";");

        if (prontuario.getRegiaoMPelvicos() != null)
            this.regiaoMPelvicos = prontuario.getRegiaoMPelvicos().split(";");

        if (prontuario.getDataAtendimento() != null)
            this.dataAtendimento = prontuario.getDataAtendimento().toString();
        else
            this.dataAtendimento = null;
    }
}

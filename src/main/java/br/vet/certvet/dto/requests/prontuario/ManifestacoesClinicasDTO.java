package br.vet.certvet.dto.requests.prontuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ManifestacoesClinicasDTO extends ProntuarioDTO {
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

    @JsonProperty("cabeca")
    private boolean cabeca;

    @JsonProperty("torax")
    private boolean torax;

    @JsonProperty("regioes_obs")
    private String regioesObs;

    @JsonProperty("apetite")
    private String apetite;

    @JsonProperty("linfonodos")
    private String linfonodos;

    @JsonProperty("linfonodos_obs")
    private String linfonodosObs;

    @JsonProperty("coluna")
    private List<String> coluna = new ArrayList<>();

    @JsonProperty("abdomen")
    private List<String> abdomen = new ArrayList<>();

    @JsonProperty("m_toracicos")
    private List<String> mToracicos = new ArrayList<>();

    @JsonProperty("m_pelvicos")
    private List<String> mPelvicos = new ArrayList<>();
}

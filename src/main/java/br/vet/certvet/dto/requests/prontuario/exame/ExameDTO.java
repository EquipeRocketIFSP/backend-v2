package br.vet.certvet.dto.requests.prontuario.exame;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class ExameDTO extends ProntuarioDTO {
    @JsonProperty("tipo_exame")
    private String tipoExame;

    @JsonProperty("bioquimico")
    private String bioquimico;

    @JsonProperty("citologia")
    private String citologia;

    @JsonProperty("hematologia")
    private String hematologia;

    @JsonProperty("exames_outros")
    @Size(max = 2000)
    private String examesOutros;

    @JsonProperty("outros_citologia")
    @Size(max = 2000)
    private String citologiaOutros;

    @JsonProperty("imagem")
    private String imagem;

    @JsonProperty("cabeca")
    private boolean cabeca;

    @JsonProperty("torax")
    private boolean torax;

    @JsonProperty("regioes_obs")
    private String regioesObs;

    @JsonProperty("linfonodos")
    private String linfonodos;

    @JsonProperty("linfonodos_obs")
    private String linfonodosObs;

    @JsonProperty("cervical")
    private String cervical;

    @JsonProperty("abdomen")
    private String abdomen;

    @JsonProperty("m_toracicos")
    private String[] mToracicos = {};

    @JsonProperty("m_pelvicos")
    private String[] mPelvicos = {};
}

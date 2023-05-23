package br.vet.certvet.dto.requests.prontuario.procedimento;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ProcedimentoDTO extends ProntuarioDTO {
    @JsonProperty("procedimento")
    private String procedimento;

    @JsonProperty("procedimento_outros")
    private String procedimentoOutros;
}

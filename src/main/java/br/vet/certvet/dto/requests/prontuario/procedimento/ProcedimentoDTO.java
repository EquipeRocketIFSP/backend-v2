package br.vet.certvet.dto.requests.prontuario.procedimento;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProcedimentoDTO extends ProntuarioDTO {
    @JsonProperty("procedimento")
    private Long procedimento;

    @JsonProperty("procedimento_outros")
    private String procedimentoOutros;

    @JsonProperty("dose")
    private BigDecimal dose;

    @JsonProperty("lote")
    private Long lote;

    @JsonProperty("medicamento")
    private Long medicamento;
}

package br.vet.certvet.dto.requests.prontuario.cirurgia;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
public class MedicamentoCirurgiaDTO extends ProntuarioDTO {
    @NotEmpty(message = "Esse campo é obrigátório")
    @JsonProperty("dose")
    private BigDecimal dose;

    @NotEmpty(message = "Esse campo é obrigátório")
    @JsonProperty("lote")
    private Long lote;

    @NotEmpty(message = "Esse campo é obrigátório")
    @JsonProperty("medicamento")
    private Long medicamento;
}

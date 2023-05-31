package br.vet.certvet.dto.requests.prontuario.cirurgia;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CirurgiaDTO extends ProntuarioDTO {
    @JsonProperty("descricao")
    @NotEmpty(message = "Esse campo é obrigátório")
    private String descricao;

    @NotNull(message = "Esse campo é obrigátório")
    @JsonProperty("data")
    private LocalDateTime data;

    @JsonProperty("medicamentos")
    private List<MedicamentoCirurgiaDTO> medicamentos;
}


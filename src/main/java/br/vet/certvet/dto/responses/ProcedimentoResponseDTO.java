package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Procedimento;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcedimentoResponseDTO {
    @JsonProperty("procedimento")
    private String procedimento;

    @JsonProperty("procedimento_outros")
    private String procedimentoOutros;

    public ProcedimentoResponseDTO(Procedimento model) {
        this.procedimento = model.getDescricao();
        this.procedimentoOutros = model.getOutros();
    }
}

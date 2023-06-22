package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Procedimento;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProcedimentoResponseDTO {
    @JsonProperty("procedimento")
    private String procedimento;

    @JsonProperty("procedimento_outros")
    private String procedimentoOutros;

    @JsonProperty("dose")
    private BigDecimal dose;

    @JsonProperty("lote")
    private EstoqueResponseDto lote;

    @JsonProperty("medicamento")
    private MedicamentoResponseDto medicamento;

    public ProcedimentoResponseDTO(Procedimento model) {
        this.procedimento = model.getProcedimentoTipo().getNome();
        this.procedimentoOutros = model.getOutros();

        if (model.getMedicamentoConsumido() != null) {
            this.dose = model.getDoseMedicamento();
            this.medicamento = new MedicamentoResponseDto(model.getMedicamentoConsumido().getMedicamento());
            this.lote = new EstoqueResponseDto(model.getMedicamentoConsumido());
        }
    }
}

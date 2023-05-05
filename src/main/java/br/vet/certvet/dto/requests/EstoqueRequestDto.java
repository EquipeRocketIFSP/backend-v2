package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EstoqueRequestDto(
        @NotNull(message = "Insira a quantidade de medicamento em estoque")
        @JsonProperty("quantidade")
        BigDecimal quantidade,

        @NotEmpty(message = "Insira a unidade de medida da quantidade")
        @JsonProperty("medida")
        String medida,

        @NotEmpty(message = "Insira o lote do medicamento")
        @JsonProperty("lote")
        String lote,

        @NotNull(message = "Insira a validade do medicamento")
        @JsonProperty("validade")
        LocalDate validade
) {
}

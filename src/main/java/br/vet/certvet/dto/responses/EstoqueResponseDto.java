package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Estoque;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class EstoqueResponseDto {
    @JsonProperty("quantidade")
    private BigDecimal quantidade;

    @JsonProperty("medida")
    private String medida;

    @JsonProperty("lote")
    private String lote;

    @JsonProperty("validade")
    private String validade;

    public EstoqueResponseDto(Estoque estoque) {
        this.quantidade = estoque.getQuantidade();
        this.lote = estoque.getLote();
        this.validade = estoque.getValidade().toString();
        this.medida = estoque.getMedida();
    }
}

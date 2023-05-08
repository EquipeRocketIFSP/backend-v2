package br.vet.certvet.dto.responses;

import br.vet.certvet.enums.TransacaoStatus;
import br.vet.certvet.models.EstoqueTransacao;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EstoqueTransacaoResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("quantidade")
    private String quantidade;

    @JsonProperty("motivo")
    private String motivo;

    @JsonProperty("data")
    private String data;

    @JsonProperty("responsavel")
    private String responsavel;

    public EstoqueTransacaoResponseDto(EstoqueTransacao transacao) {
        this.id = transacao.getId();
        this.quantidade = transacao.getQuantidade().toString();
        this.motivo = transacao.getMotivo();
        this.data = transacao.getData().toString();
        this.status = transacao.isStatus() == TransacaoStatus.ENTRY.getStatus() ? "Entrada" : "Saída";
        this.responsavel = transacao.getResponsavel().getNome();
    }
}

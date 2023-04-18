package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Medicamento;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MedicamentoResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("codigo_registro")
    private String codigoRegistro;

    @JsonProperty("principio_ativo")
    private String principioAtivo;

    @JsonProperty("via_uso")
    private String viaUso;

    @JsonProperty("concentracao")
    private String concentracao;

    @JsonProperty("fabricante")
    private String fabricante;

    @JsonProperty("nome_referencia")
    private String nomeReferencia;

    @JsonProperty("vencimento_registro")
    private String vencimentoRegistro;

    public MedicamentoResponseDto(Medicamento medicamento) {
        this.id = medicamento.getId();
        this.nome = medicamento.getNome();
        this.codigoRegistro = medicamento.getCodigoRegistro();
        this.principioAtivo = medicamento.getPrincipioAtivo();
        this.viaUso = medicamento.getViaUso();
        this.concentracao = medicamento.getConcentracao();
        this.fabricante = medicamento.getFabricante();
        this.nomeReferencia = medicamento.getNomeReferencia();
        this.vencimentoRegistro = medicamento.getVencimentoRegistro().toString();
    }
}

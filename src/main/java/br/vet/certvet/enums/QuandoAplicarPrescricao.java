package br.vet.certvet.enums;

import lombok.Getter;

@Getter
public enum QuandoAplicarPrescricao {
    JEJUM("Em Jejum"),
    APOS_ALIMENTACAO("Após Alimentação"),
    DURANTE_ALIMENTACAO("Junto do Alimento");
    private final String aplicacao;

    QuandoAplicarPrescricao(String aplicacao) {
        this.aplicacao = aplicacao;
    }
}

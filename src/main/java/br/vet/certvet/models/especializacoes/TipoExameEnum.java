package br.vet.certvet.models.especializacoes;

import lombok.Getter;

@Getter
public enum TipoExameEnum {
    BIOQUIMICO("BIOQUIMICO"),
    CITOLOGIA("CITOLOGIA"),
    HEMATOLOGIA("HEMATOLOGIA"),
    IMAGEM("IMAGEM"),
    OUTROS("OUTROS");

    private final String tipo;

    TipoExameEnum(String tipo) {
        this.tipo = tipo;
    }
}

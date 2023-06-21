package br.vet.certvet.models.manifestacoes_clinicas;

import lombok.Getter;

@Getter
public enum ColunaRegioesEnum {
    TORACICA("Torácica"),
    CERVICAL("Cervical"),
    LOMBAR("Lombar"),
    SACRAL("Sacral"),
    CAUDAL("Caudal");

    private final String coluna;

    ColunaRegioesEnum(String coluna) {
        this.coluna = coluna;
    }
}

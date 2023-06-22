package br.vet.certvet.models.manifestacoes_clinicas;

import lombok.Getter;

@Getter
public enum AbdomenRegioesEnum {
    EPIGASTRICA("Epigástrica"),
    MESOGASTRICA("Mesogástrica"),
    HIPOGASTRICA("Hipogástrica");

    private final String abdomen;

    AbdomenRegioesEnum(String abdomen) {
        this.abdomen = abdomen;
    }
}

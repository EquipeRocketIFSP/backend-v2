package br.vet.certvet.models.manifestacoes_clinicas;

import lombok.Getter;

@Getter
public enum LinfonodosEnum {
    Mandibular("Mandibular"),
    Cervical("Cervical"),
    MAMARIOS("Mamários"),
    INGUINAL("Inguinal"),
    POPILITEO("Popiliteo"),
    OUTRAS("Outras");

    private final String linfonodo;

    LinfonodosEnum(String linfonodo) {
        this.linfonodo = linfonodo;
    }
}

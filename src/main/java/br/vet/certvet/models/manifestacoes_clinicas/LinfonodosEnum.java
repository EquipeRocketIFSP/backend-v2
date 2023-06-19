package br.vet.certvet.models.manifestacoes_clinicas;

import lombok.Getter;

@Getter
public enum LinfonodosEnum {
    MANDIBULAR("Mandibular"),
    CERVICAL( "Cervical"),
    MAMARIOS("Mamários"),
    INGUINAL("Inguinal"),
    POPILITEO("Popiliteo"),
    OUTRAS("Outras");

    private final String linfonodo;

    LinfonodosEnum(String linfonodo) {
        this.linfonodo = linfonodo;
    }
}

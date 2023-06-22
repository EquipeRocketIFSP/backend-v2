package br.vet.certvet.models.manifestacoes_clinicas;

import lombok.Getter;

@Getter
public enum MusculosEnum {
    TORACICO_DIREITO("Torácico Direito"),
    TORACICO_ESQUERDO("Torácico Esquerdo"),
    TORACICO_PROXIMAL("Torácico Proximal"),
    TORACICO_DISTRITAL("Torácico Distrital"),
    PELVICO_DIREITO("Pélvicos Direito"),
    PELVICO_ESQUERDO("Pélvicos Esquerdo"),
    PELVICO_PROXIMAL("Pélvicos Proximal"),
    PELVICO_DISTRITAL("Pélvicos Distrital");

    private final String musculos;

    MusculosEnum(String musculos) {
        this.musculos = musculos;
    }
}

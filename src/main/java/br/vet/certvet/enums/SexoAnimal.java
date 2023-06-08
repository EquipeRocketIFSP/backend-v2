package br.vet.certvet.enums;

import br.vet.certvet.enums.helper.SexoAnimalConverter;
import lombok.Getter;

import javax.persistence.Convert;

@Getter
@Convert(converter = SexoAnimalConverter.class)
public enum SexoAnimal {
    MACHO("M", false),
    FEMEA("F", true);

    private final String sexo;
    private final Boolean bitValue;

    SexoAnimal(String sexo, Boolean bitValue) {
        this.sexo = sexo;
        this.bitValue = bitValue;
    }
}

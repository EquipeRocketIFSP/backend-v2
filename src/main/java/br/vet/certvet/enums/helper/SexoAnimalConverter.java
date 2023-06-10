package br.vet.certvet.enums.helper;

import br.vet.certvet.enums.SexoAnimal;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class SexoAnimalConverter implements AttributeConverter<SexoAnimal, Boolean> {
    @Override
    public Boolean convertToDatabaseColumn(SexoAnimal sexoAnimal) {
        return sexoAnimal.getBitValue();
    }

    @Override
    public SexoAnimal convertToEntityAttribute(Boolean bitValue) {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid bit value: " + bitValue);
        // Guarding pattern
        if (bitValue == null) throw exception;

        return Arrays.stream(
                SexoAnimal.values())
                .filter(
                        s -> s.getBitValue()
                                .equals(bitValue))
                .findFirst()
                .orElseThrow(() -> exception);
    }
}
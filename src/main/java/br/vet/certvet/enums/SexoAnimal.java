package br.vet.certvet.enums;



public enum SexoAnimal {
    MACHO("M", false),
    FEMEA("F", true);

    private final String sexo;
    private final Boolean bitValue;

    SexoAnimal(String sexo, Boolean bitValue) {
        this.sexo = sexo;
        this.bitValue = bitValue;
    }

    public String getSexo() {
        return sexo;
    }

    public Boolean getBitValue() {
        return bitValue;
    }
}


package br.vet.certvet.enums;

public enum SexoAnimal {
    MACHO("M"), FEMEA("F");

    private final String sexo;

    SexoAnimal(String sexo) {
        this.sexo = sexo;
    }

    public String getSigla() {
        return sexo;
    }
}

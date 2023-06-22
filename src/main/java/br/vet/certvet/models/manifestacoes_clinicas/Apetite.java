package br.vet.certvet.models.manifestacoes_clinicas;


import lombok.Getter;

@Getter
public enum Apetite {
    NORMAL(0, "normal"),
    REDUZIDO(1, "reduzido"),
    AUMENTADO(2, "aumentado");

    private final int code;
    private final String status;

    Apetite(int code, String status){
        this.code = code;
        this.status = status;
    }

}

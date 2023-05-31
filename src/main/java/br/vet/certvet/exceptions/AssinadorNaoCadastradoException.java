package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AssinadorNaoCadastradoException extends RuntimeException {
    public AssinadorNaoCadastradoException(String message) {
        super(message);
    }
}

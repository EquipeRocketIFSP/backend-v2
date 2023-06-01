package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErroMapeamentoRespostaException extends Throwable {
    public ErroMapeamentoRespostaException(String message) {
        super(message);
    }
}

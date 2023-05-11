package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProntuarioNotFoundException extends RuntimeException {
    public ProntuarioNotFoundException(String message){
        super(message);
    }
}

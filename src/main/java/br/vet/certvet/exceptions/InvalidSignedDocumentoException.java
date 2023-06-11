package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidSignedDocumentoException extends RuntimeException {
    public InvalidSignedDocumentoException(String message) {
        super(message);
    }
}

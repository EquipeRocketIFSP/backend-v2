package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DocumentoNotPersistedException extends RuntimeException {
    public DocumentoNotPersistedException(String message) {
        super(message);
    }
}

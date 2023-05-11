package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DocumentoNotFoundException extends RuntimeException {
    public DocumentoNotFoundException(String message){
        super(message);
    }
}

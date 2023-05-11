package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotSupportedDocumentoTipoException extends RuntimeException {
    public NotSupportedDocumentoTipoException(String message){
        super(message);
    }
}

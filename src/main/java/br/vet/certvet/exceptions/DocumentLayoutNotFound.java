package br.vet.certvet.exceptions;

public class DocumentLayoutNotFound extends RuntimeException {
    public DocumentLayoutNotFound(String message){
        super(message);
    }
}

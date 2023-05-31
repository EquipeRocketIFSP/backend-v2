package br.vet.certvet.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException() {
        super("Recurso já existe.");
    }

    public ConflictException(String message) {
        super(message);
    }
}

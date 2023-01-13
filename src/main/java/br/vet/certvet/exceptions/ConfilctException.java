package br.vet.certvet.exceptions;

public class ConfilctException extends RuntimeException {
    public ConfilctException() {
        super("Recurso já existe.");
    }

    public ConfilctException(String message) {
        super(message);
    }
}

package br.vet.certvet.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Ação não permitida");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}

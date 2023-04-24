package br.vet.certvet.exceptions;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException() {
        super("Não foi possivel processar esses dados");
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}

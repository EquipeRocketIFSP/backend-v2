package br.vet.certvet.exceptions;

public class UnprocessableEntityException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Não foi possivel processar esses dados";

    public UnprocessableEntityException() {
        super(UnprocessableEntityException.DEFAULT_MESSAGE);
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(Throwable cause) {
        super(UnprocessableEntityException.DEFAULT_MESSAGE, cause);
    }
}

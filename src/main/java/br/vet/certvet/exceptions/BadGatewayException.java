package br.vet.certvet.exceptions;

public class BadGatewayException extends RuntimeException {
    public BadGatewayException() {
        super("Não foi possivel conectar-se com o servidor externo");
    }

    public BadGatewayException(String message) {
        super(message);
    }
}

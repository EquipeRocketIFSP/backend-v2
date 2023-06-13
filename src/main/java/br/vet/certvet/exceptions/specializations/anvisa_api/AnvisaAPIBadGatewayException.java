package br.vet.certvet.exceptions.specializations.anvisa_api;

import br.vet.certvet.exceptions.BadGatewayException;

public class AnvisaAPIBadGatewayException extends BadGatewayException {
    public AnvisaAPIBadGatewayException(Throwable cause) {
        super("Não foi possivel conectar-se com a API da Anvisa", cause);
    }
}

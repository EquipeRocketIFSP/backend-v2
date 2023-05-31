package br.vet.certvet.exceptions.specializations.anvisaApi;

import br.vet.certvet.exceptions.BadGatewayException;

public class AnvisaAPIBadGatewayException extends BadGatewayException {
    public AnvisaAPIBadGatewayException(Throwable cause) {
        super("Não foi possivel conectar-se com a API da Anvisa", cause);
    }
}

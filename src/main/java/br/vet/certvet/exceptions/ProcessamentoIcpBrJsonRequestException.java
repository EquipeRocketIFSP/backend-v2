package br.vet.certvet.exceptions;

import java.io.IOException;

public class ProcessamentoIcpBrJsonRequestException extends IOException {
    public ProcessamentoIcpBrJsonRequestException(String message) {
        super(message);
    }
}

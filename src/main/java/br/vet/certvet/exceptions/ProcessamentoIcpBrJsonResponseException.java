package br.vet.certvet.exceptions;

import java.io.IOException;

public class ProcessamentoIcpBrJsonResponseException extends IOException {
    public ProcessamentoIcpBrJsonResponseException(String message) {
        super(message);
    }
}

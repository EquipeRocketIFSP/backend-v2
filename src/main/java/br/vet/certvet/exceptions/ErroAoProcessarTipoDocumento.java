package br.vet.certvet.exceptions;

import java.io.IOException;

public class ErroAoProcessarTipoDocumento extends RuntimeException {
    public ErroAoProcessarTipoDocumento(IOException e) {
        super(e);
    }
}

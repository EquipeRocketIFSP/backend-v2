package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PdfNaoReconhecidoException extends RuntimeException {
    public PdfNaoReconhecidoException(String s) {
        super(s);
    }
}

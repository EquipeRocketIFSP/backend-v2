package br.vet.certvet.exceptions;

public class PdfMauFormadoException extends RuntimeException {
    public PdfMauFormadoException(String message, Exception e) {
        super(message, e);
    }
}

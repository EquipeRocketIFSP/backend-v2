package br.vet.certvet.exceptions;

public class NotMatchingFileTypeToPdfException extends RuntimeException {
    public NotMatchingFileTypeToPdfException(String e) {
        super(e);
    }
}

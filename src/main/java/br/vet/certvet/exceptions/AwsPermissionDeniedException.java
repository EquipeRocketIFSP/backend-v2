package br.vet.certvet.exceptions;

public class AwsPermissionDeniedException extends RuntimeException {
    public AwsPermissionDeniedException(String message) {
        super(message);
    }
}

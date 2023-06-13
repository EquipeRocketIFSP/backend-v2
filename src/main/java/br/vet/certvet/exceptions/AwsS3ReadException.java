package br.vet.certvet.exceptions;


public class AwsS3ReadException extends RuntimeException {
    public AwsS3ReadException(String localizedMessage, Exception e) {
        super(localizedMessage, e);
    }
}

package br.vet.certvet.exceptions;


public class AwsS3WritingException extends RuntimeException {
    public AwsS3WritingException(String localizedMessage, Exception e) {
        super(localizedMessage, e);
    }
}

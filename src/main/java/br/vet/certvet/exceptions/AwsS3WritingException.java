package br.vet.certvet.exceptions;

import com.amazonaws.AmazonServiceException;

public class AwsS3WritingException extends AmazonServiceException {
    public AwsS3WritingException(String localizedMessage, Exception e) {
        super(localizedMessage, e);
    }
}

package br.vet.certvet.exceptions;

import com.amazonaws.services.s3.model.AmazonS3Exception;

public class AwsS3ReadException extends AmazonS3Exception {
    public AwsS3ReadException(String localizedMessage, Exception e) {
        super(localizedMessage, e);
    }
}

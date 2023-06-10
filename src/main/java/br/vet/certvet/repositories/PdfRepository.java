package br.vet.certvet.repositories;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.IOException;
import java.util.Optional;

public interface PdfRepository {
    ObjectMetadata putObject(String cnpj, String keyName, byte[] bynaryArrayInputStream);

    Optional<byte[]> retrieveObject(String cnpj, String keyName) throws IOException;

    Boolean setPublicFileReadingPermission(final String bucket, Boolean allow);

    boolean exists(String cnpj, String fileName);

}
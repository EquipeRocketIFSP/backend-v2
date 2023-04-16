package br.vet.certvet.repositories;

import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.IOException;

public interface PdfRepository {
    PutObjectResult putObject(String cnpj, String keyName, byte[] bynaryArrayInputStream);

    byte[] retrieveObject(String cnpj, String keyName) throws IOException;
}
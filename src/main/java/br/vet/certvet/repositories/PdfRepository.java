package br.vet.certvet.repositories;

import java.io.File;
import java.io.IOException;

public interface PdfRepository {
    void putObject(String cnpj, String keyName, File filePath);

    byte[] retrieveObject(String cnpj, String keyName) throws IOException;
}
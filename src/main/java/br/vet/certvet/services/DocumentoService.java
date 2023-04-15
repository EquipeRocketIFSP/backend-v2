package br.vet.certvet.services;

import br.vet.certvet.exceptions.DocumentLayoutNotFound;
import br.vet.certvet.models.Documento;
import org.springframework.stereotype.Service;

@Service
public interface DocumentoService {
    Documento provideLayout(String layout) throws DocumentLayoutNotFound;
}

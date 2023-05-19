package br.vet.certvet.services.implementation;

import br.vet.certvet.exceptions.DocumentLayoutNotFound;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.especializacoes.*;
import br.vet.certvet.repositories.DocumentoRepository;
import br.vet.certvet.services.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public Optional<Documento> getByCodigo(String documentoCodigo) {
        return documentoRepository.findByCodigo(documentoCodigo);
    }

    @Override
    public Documento save(Documento documento) {
        return documentoRepository.save(documento);
    }
}

package br.vet.certvet.services;

import br.vet.certvet.models.Prontuario;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public interface ProntuarioService {

    Optional<Prontuario> findById(Long id);

    byte[] retrieveFromRepository(Prontuario prontuario) throws IOException;
}

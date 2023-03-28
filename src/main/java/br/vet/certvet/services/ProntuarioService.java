package br.vet.certvet.services;

import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface ProntuarioService {

    Optional<Prontuario> findById(Long id);

    byte[] retrieveFromRepository(Prontuario prontuario) throws IOException;
    Optional<Prontuario> createProntuario(Prontuario prontuario);

    Optional<Prontuario> editProntuario(Prontuario prontuario);

    Optional<Prontuario> getProntuarioById(Long id);

    Boolean deleteProntuario(Prontuario prontuario);

    byte[] getProntuarioPdfBy(Long id);
    byte[] getProntuarioPdfBy(Prontuario prontuario);

    List<Documento> getDocumentosTipo(Long prontuarioId, String tipo);
}

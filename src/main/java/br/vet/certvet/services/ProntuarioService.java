package br.vet.certvet.services;

import br.vet.certvet.models.Prontuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProntuarioService {

    Optional<Prontuario> createProntuario(Prontuario prontuario);

    Optional<Prontuario> editProntuario(Prontuario prontuario);

    Optional<Prontuario> getProntuarioById(Long id);

    Boolean deleteProntuario(Prontuario prontuario);

    byte[] getProntuarioPdfBy(Long id);
    byte[] getProntuarioPdfBy(Prontuario prontuario);
}

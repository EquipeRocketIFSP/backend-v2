package br.vet.certvet.services;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProntuarioService {

    Prontuario create(ProntuarioDTO dto, Animal animal, Usuario tutor, Usuario veterinario);

    Optional<Prontuario> editProntuario(Prontuario prontuario);

    Optional<Prontuario> getProntuarioById(Long id);

    Boolean deleteProntuario(Prontuario prontuario);

    byte[] getProntuarioPdfBy(Long id);

    byte[] getProntuarioPdfBy(Prontuario prontuario);
}

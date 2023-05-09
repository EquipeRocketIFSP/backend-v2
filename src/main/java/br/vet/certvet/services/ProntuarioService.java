package br.vet.certvet.services;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface ProntuarioService {

    Prontuario create(ProntuarioDTO dto, Animal animal, Usuario tutor, Usuario veterinario);

    Prontuario edit(ProntuarioDTO dto, Prontuario prontuario);

    Prontuario findOne(Long id, Animal animal);

    Boolean deleteProntuario(Prontuario prontuario);

    byte[] getProntuarioPdfBy(Long id);

    byte[] getProntuarioPdfBy(Prontuario prontuario);
}

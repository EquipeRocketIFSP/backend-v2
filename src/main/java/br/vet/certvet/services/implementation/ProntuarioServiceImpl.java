package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.models.factories.ProntuarioFactory;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.ProntuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProntuarioServiceImpl implements ProntuarioService {
    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Override
    public Prontuario create(ProntuarioDTO dto, Animal animal, Usuario tutor, Usuario veterinario) {
        Prontuario prontuario = ProntuarioFactory.factory(dto)
                .setAnimal(animal)
                .setTutor(tutor)
                .setVeterinario(veterinario);

        return this.prontuarioRepository.saveAndFlush(prontuario);
    }

    @Override
    public Optional<Prontuario> editProntuario(Prontuario prontuario) {
        return Optional.empty();
    }

    @Override
    public Optional<Prontuario> getProntuarioById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteProntuario(Prontuario prontuario) {
        return Boolean.FALSE;
    }

    @Override
    public byte[] getProntuarioPdfBy(Long id) {
        return new byte[0];
    }

    @Override
    public byte[] getProntuarioPdfBy(Prontuario prontuario) {
        return new byte[0];
    }
}

package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.AnimalRepository;
import br.vet.certvet.services.AnimalService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public Animal create(AnimalRequestDto dto, List<Usuario> tutores) {
        Animal animal = new Animal(dto);
        animal.getTutores().addAll(tutores);

        return this.animalRepository.saveAndFlush(animal);
    }
}

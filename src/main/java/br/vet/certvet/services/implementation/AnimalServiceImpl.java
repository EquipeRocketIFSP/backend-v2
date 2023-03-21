package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.dto.responses.AnimalResponseDto;
import br.vet.certvet.dto.responses.Metadata;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.exceptions.ForbiddenException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.AnimalRepository;
import br.vet.certvet.services.AnimalService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AnimalRepository animalRepository;

    private static final short RESPONSE_LIMIT = 30;

    @Override
    public Animal create(AnimalRequestDto dto, List<Usuario> tutores) {
        AnimalServiceImpl.checkTutorAuthority(tutores, this.usuarioService);

        Animal animal = new Animal(dto);
        animal.getTutores().addAll(tutores);

        return this.animalRepository.saveAndFlush(animal);
    }

    @Override
    public Animal edit(AnimalRequestDto dto, Animal animal, List<Usuario> tutores) {
        AnimalServiceImpl.checkTutorAuthority(tutores, this.usuarioService);

        animal.fill(dto);
        animal.getTutores().clear();
        animal.getTutores().addAll(tutores);

        //System.out.println(tutores.toString());

        return this.animalRepository.saveAndFlush(animal);
    }

    @Override
    public Animal findOne(Long id) {
        Optional<Animal> response = this.animalRepository.findById(id);

        if (response.isEmpty())
            throw new NotFoundException("Animal não encontrado");

        return response.get();
    }

    @Override
    public PaginatedResponse<AnimalResponseDto> findAll(int page, String search, String url, Usuario tutor) {
        page = Math.max(page, 1);

        Pageable pageable = PageRequest.of(page - 1, AnimalServiceImpl.RESPONSE_LIMIT);

        Long total = search.trim().isEmpty() ?
                this.animalRepository.countByTutores(tutor) :
                this.animalRepository.countByNomeContainingAndTutores(search, tutor);

        Metadata metadata = new Metadata(url, page, AnimalServiceImpl.RESPONSE_LIMIT, total);

        List<Animal> animais = search.trim().isEmpty() ?
                this.animalRepository.findAllByTutores(pageable, tutor) :
                this.animalRepository.findAllByNomeContainingAndTutores(pageable, search, tutor);

        List<AnimalResponseDto> animalResponseDtos = animais.stream()
                .map(animal -> new AnimalResponseDto(animal))
                .toList();

        return new PaginatedResponse<>(metadata, animalResponseDtos);
    }

    private static void checkTutorAuthority(List<Usuario> tutores, UsuarioService usuarioService) throws ForbiddenException {
        tutores.forEach((tutor) -> {
            Optional<Authority> response = usuarioService.findUsuarioAuthority(tutor, "TUTOR");

            if (response.isEmpty())
                throw new ForbiddenException("Um dos usuários não é um tutor");
        });
    }
}

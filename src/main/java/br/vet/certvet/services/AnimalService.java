package br.vet.certvet.services;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.dto.responses.AnimalResponseDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnimalService {
    Animal create(AnimalRequestDto dto, List<Usuario> tutores);

    Animal edit(AnimalRequestDto dto, Animal animal, List<Usuario> tutores);

    Animal findOne(Long id, Usuario tutor);

    PaginatedResponse<AnimalResponseDto> findAll(int page, String search, String url, Usuario tutor);
}

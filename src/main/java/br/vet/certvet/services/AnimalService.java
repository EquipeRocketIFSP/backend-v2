package br.vet.certvet.services;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.models.Animal;
import org.springframework.stereotype.Service;

@Service
public interface AnimalService {
    public Animal create(AnimalRequestDto dto);
}

package br.vet.certvet.services;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnimalService {
    public Animal create(AnimalRequestDto dto, List<Usuario> tutores);
}
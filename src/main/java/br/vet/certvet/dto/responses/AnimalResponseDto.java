package br.vet.certvet.dto.responses;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Usuario;

import java.util.stream.Collectors;

public class AnimalResponseDto extends AnimalRequestDto {
    public Long id;

    public AnimalResponseDto(Animal animal) {
        this.id = animal.getId();
        this.nome = animal.getNome();
        this.especie = animal.getEspecie();
        this.idade = animal.getIdade();
        this.pelagem = animal.getPelagem();
        this.raca = animal.getRaca();
        this.sexo = String.valueOf(animal.getSexo());
        this.tutores = animal.getTutores().stream()
                .map(Usuario::getId)
                .collect(Collectors.toList());
    }
}

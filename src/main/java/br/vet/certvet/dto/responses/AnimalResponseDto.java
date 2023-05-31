package br.vet.certvet.dto.responses;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.models.Animal;

import java.util.List;
import java.util.stream.Collectors;

public class AnimalResponseDto extends AnimalRequestDto {
    public Long id;
    public List<UsuarioResponseDto> tutores;

    public AnimalResponseDto(Animal animal) {
        this.id = animal.getId();
        this.nome = animal.getNome();
        this.especie = animal.getEspecie();
        this.ano_nascimento = animal.getAnoNascimento();
        this.peso = animal.getPeso();
        this.pelagem = animal.getPelagem();
        this.raca = animal.getRaca();
        this.sexo = String.valueOf(animal.getSexo());
        this.tutores = animal.getTutores().stream()
                .map(UsuarioResponseDto::new)
                .collect(Collectors.toList());
    }
}

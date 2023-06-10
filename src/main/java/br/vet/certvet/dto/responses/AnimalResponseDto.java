package br.vet.certvet.dto.responses;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.models.Animal;
import lombok.Getter;

import java.util.List;

@Getter
public class AnimalResponseDto extends AnimalRequestDto {
    private final Long id;
    private final List<UsuarioResponseDto> tutoresDto;

    public AnimalResponseDto(Animal animal) {
        this.id = animal.getId();
        this.nome = animal.getNome();
        this.especie = animal.getEspecie();
        this.anoNascimento = animal.getAnoNascimento();
        this.peso = animal.getPeso();
        this.pelagem = animal.getPelagem();
        this.raca = animal.getRaca();
        this.sexo = String.valueOf(animal.getSexo());
        this.tutoresDto = animal.getTutores().stream()
                .map(UsuarioResponseDto::new)
                .toList();
    }
}

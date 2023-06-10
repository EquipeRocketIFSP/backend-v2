package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

public record AnimalResponseDto(
        Long id,

        @NotEmpty(message = "Nome não pode ser vazio")
        String nome,

        @NotNull(message = "Ano de nascimento não pode ser vazio")
        @Positive(message = "Ano de nascimento não pode ser negativo")
        @JsonProperty("ano_nascimento")
        int anoNascimento,

        @NotNull(message = "Peso não pode ser vazio")
        @Positive(message = "Peso não pode ser negativo")
        float peso,

        @NotEmpty(message = "Raça não pode ser vazio")
        String raca,

        @NotEmpty(message = "Espécie não pode ser vazio")
        String especie,

        @NotEmpty(message = "Pelagem não pode ser vazio")
        String pelagem,

        @NotEmpty(message = "Sexo não pode ser vazio")
        @Pattern(regexp = "(MACHO|FEMEA)", message = "Valores aceitos para sexo são: MACHO ou FEMEA")
        String sexo,

        @NotEmpty(message = "Selecione um ou mais tutores")
        List<Long> tutores,

        List<UsuarioResponseDto> tutoresDto
) {
    public static AnimalResponseDto of(Animal animal) {
        return new AnimalResponseDto(
                animal.getId(),
                animal.getNome(),
                animal.getAnoNascimento(),
                animal.getPeso(),
                animal.getRaca(),
                animal.getEspecie(),
                animal.getPelagem(),
                String.valueOf(animal.getSexo()),
                animal.getTutores().stream()
                        .mapToLong(Usuario::getId)
                        .boxed().toList(),
                animal.getTutores().stream()
                        .map(UsuarioResponseDto::new)
                        .toList()
        );
    }
}

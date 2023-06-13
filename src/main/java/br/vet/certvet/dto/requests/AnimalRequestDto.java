package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public record AnimalRequestDto(
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

        String sexo,

        @NotEmpty(message = "Selecione um ou mais tutores")
        List<Long> tutores
) {
}

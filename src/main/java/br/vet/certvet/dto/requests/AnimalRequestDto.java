package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public abstract class AnimalRequestDto {
    @NotEmpty(message = "Nome não pode ser vazio")
    protected String nome;

    @NotNull(message = "Ano de nascimento não pode ser vazio")
    @Positive(message = "Ano de nascimento não pode ser negativo")
    @JsonProperty("ano_nascimento")
    protected int anoNascimento;

    @NotNull(message = "Peso não pode ser vazio")
    @Positive(message = "Peso não pode ser negativo")
    protected float peso;

    @NotEmpty(message = "Raça não pode ser vazio")
    protected String raca;

    @NotEmpty(message = "Espécie não pode ser vazio")
    protected String especie;

    @NotEmpty(message = "Pelagem não pode ser vazio")
    protected String pelagem;

    @NotEmpty(message = "Sexo não pode ser vazio")
    @Pattern(regexp = "(MACHO|FEMEA)", message = "Valores aceitos para sexo são: MACHO ou FEMEA")
    protected String sexo;

    @NotEmpty(message = "Selecione um ou mais tutores")
    protected List<Long> tutores;
}

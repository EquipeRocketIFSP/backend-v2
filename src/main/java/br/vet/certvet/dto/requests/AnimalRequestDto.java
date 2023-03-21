package br.vet.certvet.dto.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

public class AnimalRequestDto {
    @NotEmpty(message = "Nome não pode ser vazio")
    public String nome;

    @NotNull(message = "Idade não pode ser vazio")
    @Positive(message = "Idade não pode ser negativo")
    public int idade;

    @NotNull(message = "Peso não pode ser vazio")
    @Positive(message = "Peso não pode ser negativo")
    public float peso;

    @NotEmpty(message = "Raça não pode ser vazio")
    public String raca;

    @NotEmpty(message = "Espécie não pode ser vazio")
    public String especie;

    @NotEmpty(message = "Pelagem não pode ser vazio")
    public String pelagem;

    @NotEmpty(message = "Sexo não pode ser vazio")
    @Pattern(regexp = "(MACHO|FEMEA)", message = "Valores aceitos para sexo são: MACHO ou FEMEA")
    public String sexo;

    @NotEmpty(message = "Selecione um ou mais tutores")
    public List<Long> tutores;
}

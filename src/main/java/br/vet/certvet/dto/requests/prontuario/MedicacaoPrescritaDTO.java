package br.vet.certvet.dto.requests.prontuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class MedicacaoPrescritaDTO {
    @JsonProperty("uso")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String uso;

    @JsonProperty("nome")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String nome;

    @JsonProperty("dose")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String dose;

    @JsonProperty("forma_farmaceutica")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String formaFarmaceutica;

    @JsonProperty("concentracao")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String concentracao;

    @JsonProperty("frequencia")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String frequencia;

    @JsonProperty("duracao")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String duracao;

    @JsonProperty("quando_aplicar")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String quandoAplicar;

    @JsonProperty("observacoes")
    @Size(max = 2000, message = "Tamanho máximo desse campo é 2000 caracteres")
    private String observacoes;
}

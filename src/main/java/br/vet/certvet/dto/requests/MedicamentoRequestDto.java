package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record MedicamentoRequestDto(
        @NotEmpty(message = "Por favor insira o código de registro do medicamento")
        @Size(max = 255, message = "O código de registro do medicamento não pode ultrpassar 255 caracteres")
        @JsonProperty("codigo_registro")
        String codigoRegistro,

        @NotEmpty(message = "Por favor insira o nome do medicamento")
        @Size(max = 255, message = "O nome do medicamento do medicamento não pode ultrpassar 255 caracteres")
        @JsonProperty("nome")
        String nome,

        @NotEmpty(message = "Por favor insira o princípio ativo do medicamento")
        @Size(max = 255, message = "O princípio ativo do medicamento não pode ultrpassar 255 caracteres")
        @JsonProperty("principio_ativo")
        String principioAtivo,

        @NotEmpty(message = "Por favor insira a via de uso do medicamento")
        @Size(max = 255, message = "A via de uso do medicamento não pode ultrpassar 255 caracteres")
        @JsonProperty("via_uso")
        String viaUso,

        @NotEmpty(message = "Por favor insira a concentração do medicamento")
        @Size(max = 255, message = "A concentração do medicamento não pode ultrpassar 255 caracteres")
        @JsonProperty("concentracao")
        String concentracao,

        @NotEmpty(message = "Por favor insira o fabricante do medicamento")
        @Size(max = 255, message = "O fabricante do medicamento não pode ultrpassar 255 caracteres")
        @JsonProperty("fabricante")
        String fabricante,

        @NotEmpty(message = "Por favor insira a apresentação do medicamento")
        @Size(max = 255, message = "A apresentação do medicamento não pode ultrpassar 255 caracteres")
        @JsonProperty("apresentacao")
        String apresentacao
) {
}

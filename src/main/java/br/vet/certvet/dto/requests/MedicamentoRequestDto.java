package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record MedicamentoRequestDto(
        @NotEmpty(message = "Por favor insira o código de registro do medicamento")
        @Size(max = 255, message = "O código de registro do medicamento não pode ultrpassar 255 caracteres")
        @JsonProperty("codigo_registro")
        String codigoRegistro
) {
}

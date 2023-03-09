package br.vet.certvet.dto.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PasswordResetEmailRequestDto {
    @NotNull(message = "Insira o ID da clínica")
    public Long clinica;

    @NotEmpty(message = "Insira um e-mail válido")
    public String email;
}

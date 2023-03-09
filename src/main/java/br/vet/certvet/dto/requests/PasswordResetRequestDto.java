package br.vet.certvet.dto.requests;

import javax.validation.constraints.NotEmpty;

public class PasswordResetRequestDto {
    @NotEmpty(message = "Insira a nova senha")
    public String senha;

    @NotEmpty(message = "Token não pode estar vazio")
    public String token;
}

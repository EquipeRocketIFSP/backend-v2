package br.vet.certvet.dto.requests;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginRequestDto {
    @NotNull(message = "Insira o ID da clínica")
    public Long clinica;

    @NotEmpty(message = "Insira um e-mail válido")
    public String email;

    @NotEmpty(message = "Insira uma senha")
    public String senha;

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(this.email + " - " + this.clinica, this.senha);
    }
}

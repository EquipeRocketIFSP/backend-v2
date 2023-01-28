package br.vet.certvet.dto.requests;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

public class LoginRequestDto {
    @NotEmpty(message = "Insira o CNPJ da clínica")
    public String cnpj_clinica;

    @NotEmpty(message = "Insira um e-mail válido")
    public String email;

    @NotEmpty(message = "Insira uma senha")
    public String senha;

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}

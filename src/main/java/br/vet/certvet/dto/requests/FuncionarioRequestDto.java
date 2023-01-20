package br.vet.certvet.dto.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class FuncionarioRequestDto extends UsuarioRequestDto {
    @NotEmpty(message = "Senha não pode estar vazio")
    @Size(max = 255, message = "Senha não pode ultrapassar 255 caracteres")
    public String senha;
}

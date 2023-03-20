package br.vet.certvet.dto.requests;

import javax.validation.constraints.Size;

public class FuncionarioEditRequestDto extends UsuarioRequestDto {
    @Size(max = 255, message = "Senha não pode ultrapassar 255 caracteres")
    public String senha;
}

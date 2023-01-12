package br.vet.certvet.dto.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UsuarioAtivoRequestDto extends UsuarioRequestDto {

    @Size(max = 255, message = "CRMV não pode ultrapassar 255 caracteres")
    public String crmv;
    @NotEmpty(message = "Senha não pode estar vazio")
    @Size(max = 255, message = "Senha não pode ultrapassar 255 caracteres")
    public String senha;
}

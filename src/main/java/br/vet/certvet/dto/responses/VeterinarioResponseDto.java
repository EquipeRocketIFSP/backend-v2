package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Usuario;

public class VeterinarioResponseDto extends UsuarioResponseDto {
    public String crmv;

    public VeterinarioResponseDto(Usuario usuario) {
        super(usuario);

        this.crmv = usuario.getCrmv();
    }
}

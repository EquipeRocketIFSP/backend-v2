package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class VeterinarioResponseDto extends UsuarioResponseDto {
    @JsonProperty("crmv")
    private String crmv;

    @JsonProperty("is_techinical_responsible")
    private boolean isTechinicalResponsible;

    public VeterinarioResponseDto(Usuario usuario) {
        super(usuario);

        this.crmv = usuario.getCrmv();

        Optional<Usuario> technicalResponsible = Optional.ofNullable(usuario.getClinica().getResponsavelTecnico());

        if (technicalResponsible.isEmpty())
            this.isTechinicalResponsible = false;
        else
            this.isTechinicalResponsible = technicalResponsible.get().getId().equals(usuario.getId());
    }
}

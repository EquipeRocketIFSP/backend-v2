package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

@Setter
@Getter
@Accessors(chain = true)
public class FuncionarioRequestDto extends UsuarioRequestDto {
    @JsonProperty("senha")
    @Size(max = 255, message = "Senha não pode ultrapassar 255 caracteres")
    private String senha;

    @JsonProperty("is_admin")
    private boolean isAdmin = false;
}

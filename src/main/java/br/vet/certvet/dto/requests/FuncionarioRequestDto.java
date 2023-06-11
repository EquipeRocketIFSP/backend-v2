package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

@Getter
@Accessors(chain = true)
public class FuncionarioRequestDto extends UsuarioRequestDto {
    //    public boolean is_admin;
    @JsonProperty("senha")
    @Size(max = 255, message = "Senha não pode ultrapassar 255 caracteres")
    private String senha;

    @JsonProperty("is_admin")
    private boolean isAdmin = false;

    @JsonProperty("senha")
    public FuncionarioRequestDto setSenha(@Size(max = 255, message = "Senha não pode ultrapassar 255 caracteres") String senha) {
        this.senha = senha;
        return this;
    }

    @JsonProperty("is_admin")
    public FuncionarioRequestDto setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }
}

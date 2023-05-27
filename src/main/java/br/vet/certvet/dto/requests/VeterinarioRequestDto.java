package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Accessors(chain = true)
public class VeterinarioRequestDto extends FuncionarioRequestDto {
    public boolean is_admin;
    @NotEmpty(message = "CRMV não pode estar vazio")
    @Size(max = 255, message = "CRMV não pode ultrapassar 255 caracteres")
    @JsonProperty("crmv")
    private String crmv;

    @JsonProperty("is_technical_responsible")
    private boolean isTechnicalResponsible = false;
}

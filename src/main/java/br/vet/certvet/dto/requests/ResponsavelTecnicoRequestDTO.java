package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class ResponsavelTecnicoRequestDTO {
    @JsonProperty("crmv")
    @NotEmpty(message = "CRMV é obrigatório")
    private String crmv;
}

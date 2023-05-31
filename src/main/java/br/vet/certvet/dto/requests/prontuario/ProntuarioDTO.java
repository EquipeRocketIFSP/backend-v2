package br.vet.certvet.dto.requests.prontuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class ProntuarioDTO {
    @JsonProperty("animal")
    @NotNull(message = "Animal é um campo obrigatório")
    private Long animal;

    @JsonProperty("tutor")
    @NotNull(message = "Tutor é um campo obrigatório")
    private Long tutor;

    @JsonProperty("veterinario")
    @NotNull(message = "Veterinário é um campo obrigatório")
    private Long veterinario;
}

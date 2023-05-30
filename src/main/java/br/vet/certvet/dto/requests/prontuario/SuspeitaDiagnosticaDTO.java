package br.vet.certvet.dto.requests.prontuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class SuspeitaDiagnosticaDTO extends ProntuarioDTO {
    @JsonProperty("supeita_diagnostica")
    @Size(max = 2000, message = "O tamanho máximo é de 255 caracteres")
    private String supeitaDiagnostica;
}

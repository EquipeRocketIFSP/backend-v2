package br.vet.certvet.dto.requests.prontuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Accessors(chain = true)
public class SinaisVitaisDTO extends ProntuarioDTO {
    @JsonProperty("frequencia_cardiaca")
    @NotNull(message = "Frequência cardiaca é obrigatório")
    private int frequenciaCardiaca;

    @JsonProperty("frequencia_respiratoria")
    @NotNull(message = "Frequência respiratória é obrigatório")
    private int frequenciaRespiratoria;

    @JsonProperty("temperatura")
    @NotNull(message = "Temperatura é obrigatória")
    private int temperatura;

    @JsonProperty("peso")
    @NotEmpty(message = "Peso é obrigatório")
    @Size(max = 255, message = "O tamanho máximo é de 255 caracteres")
    private String peso;

    @JsonProperty("hidratacao")
    @NotEmpty(message = "Hidratação é obrigatório")
    @Size(max = 255, message = "O tamanho máximo é de 255 caracteres")
    private String hidratacao;

    @JsonProperty("tpc")
    @NotEmpty(message = "Tempo de Preenchimento Capilar é obrigatório")
    @Size(max = 255, message = "O tamanho máximo é de 255 caracteres")
    private String tpc;

    @JsonProperty("mucosa")
    @NotEmpty(message = "Selecione um estado de mucosa")
    @Size(max = 255, message = "O tamanho máximo é de 255 caracteres")
    private String mucosa;

    @JsonProperty("conciencia")
    @NotEmpty(message = "Selecione um estado de conciência")
    @Size(max = 255, message = "O tamanho máximo é de 255 caracteres")
    private String conciencia;

    @JsonProperty("escore_corporal")
    @NotEmpty(message = "Selecione um estado de escore corporal")
    @Size(max = 255, message = "O tamanho máximo é de 255 caracteres")
    private String escoreCorporal;
}

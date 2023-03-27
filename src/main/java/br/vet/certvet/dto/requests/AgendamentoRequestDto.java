package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
public class AgendamentoRequestDto {
    @NotNull(message = "Selecione um animal")
    protected Long animal;

    @NotNull(message = "Selecione o tutor responsável no momento do atendimento")
    protected Long tutor;

    @NotNull(message = "Selecione o veterinário")
    protected Long veterinario;

    @Size(max = 1000, message = "Tamanho máximo do campo de observações é de 1000 caracteres")
    protected String observacoes;

    @NotNull(message = "Informe uma data da consulta")
    @Future(message = "Informe uma data futura")
    @JsonProperty("data_consulta")
    protected LocalDateTime dataConsulta;
}

package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public record AgendamentoRequestDto(
        @NotNull(message = "Selecione um animal")
        Long animal,

        @NotNull(message = "Selecione o tutor responsável no momento do atendimento")
        Long tutor,

        @NotNull(message = "Selecione o veterinário")
        Long veterinario,

        @Size(max = 1000, message = "Tamanho máximo do campo de observações é de 1000 caracteres")
        String observacoes,

        @NotNull(message = "Informe uma data da consulta")
        @Future(message = "Informe uma data futura")
        @JsonProperty("data_consulta")
        LocalDateTime dataConsulta
) {
}

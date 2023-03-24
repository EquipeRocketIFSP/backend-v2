package br.vet.certvet.dto.requests;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class AgendamentoRequestDto {
    @NotNull(message = "Selecione um animal")
    public Long animal;

    @NotNull(message = "Selecione o tutor responsável no momento do atendimento")
    public Long tutor;

    @NotNull(message = "Selecione o veterinário")
    public Long veterinario;

    @Size(max = 1000, message = "Tamanho máximo do campo de observações é de 1000 caracteres")
    public String observacoes;

    @NotNull(message = "Informe uma data da consulta")
    @Future(message = "Informe uma data futura")
    public LocalDateTime dataConsulta;
}

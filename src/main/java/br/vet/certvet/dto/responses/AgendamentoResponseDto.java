package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Agendamento;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AgendamentoResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("data_consulta")
    private String dataConsulta;

    @JsonProperty("observacoes")
    private String observacoes;


    @JsonProperty("animal")
    private String animal;


    @JsonProperty("tutor")
    private String tutor;


    @JsonProperty("veterinario")
    private String veterinario;

    public AgendamentoResponseDto(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.observacoes = agendamento.getObservacoes();
        this.dataConsulta = agendamento.getDataConsulta().toString();
        this.animal = agendamento.getAnimal().getNome();
        this.tutor = agendamento.getTutor().getNome();
        this.veterinario = agendamento.getVeterinario().getNome();
    }

    public static AgendamentoResponseDto factory(Agendamento agendamento) {
        return new AgendamentoResponseDto(agendamento);
    }
}

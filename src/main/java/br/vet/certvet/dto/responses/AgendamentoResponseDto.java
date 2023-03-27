package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Agendamento;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AgendamentoResponseDto {
    public Long id;

    @JsonProperty("data_consulta")
    public String dataConsulta;
    public String observacoes;
    public String animal;
    public String tutor;
    public UsuarioResponseDto veterinario;

    public AgendamentoResponseDto(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.observacoes = agendamento.getObservacoes();
        this.dataConsulta = agendamento.getDataConsulta().toString();
        this.animal = agendamento.getAnimal().getNome();
        this.tutor = agendamento.getTutor().getNome();
        this.veterinario = new UsuarioResponseDto(agendamento.getVeterinario());
    }

    public static AgendamentoResponseDto factory(Agendamento agendamento) {
        return new AgendamentoResponseDto(agendamento);
    }
}

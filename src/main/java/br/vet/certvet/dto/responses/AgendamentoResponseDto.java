package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Agendamento;


public class AgendamentoResponseDto {
    public Long id;
    public String observacoes;
    public String dataConsulta;
    public String animal;
    public String tutor;

    public AgendamentoResponseDto(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.observacoes = agendamento.getObservacoes();
        this.dataConsulta = agendamento.getDataConsulta().toString();
        this.animal = agendamento.getAnimal().getNome();
        this.tutor = agendamento.getTutor().getNome();
    }
}

package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Agendamento;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AgendamentoCompleteResponseDto {
    @JsonProperty("id")
    protected Long id;

    @JsonProperty("data_consulta")
    protected String dataConsulta;

    @JsonProperty("observacoes")
    protected String observacoes;


    @JsonProperty("animal")
    protected AnimalResponseDto animal;


    @JsonProperty("tutor")
    protected UsuarioResponseDto tutor;


    @JsonProperty("veterinario")
    protected VeterinarioResponseDto veterinario;

    public AgendamentoCompleteResponseDto(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.observacoes = agendamento.getObservacoes();
        this.dataConsulta = agendamento.getDataConsulta().toString();
        this.animal = new AnimalResponseDto(agendamento.getAnimal());
        this.tutor = new UsuarioResponseDto(agendamento.getTutor());
        this.veterinario = new VeterinarioResponseDto(agendamento.getVeterinario());
    }

    public static AgendamentoCompleteResponseDto factory(Agendamento agendamento) {
        return new AgendamentoCompleteResponseDto(agendamento);
    }
}

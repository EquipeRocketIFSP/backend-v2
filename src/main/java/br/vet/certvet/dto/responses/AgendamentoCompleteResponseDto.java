package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Agendamento;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AgendamentoCompleteResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("data_consulta")
    private String dataConsulta;

    @JsonProperty("observacoes")
    private String observacoes;


    @JsonProperty("animal")
    private AnimalResponseDto animal;


    @JsonProperty("tutor")
    private UsuarioResponseDto tutor;


    @JsonProperty("veterinario")
    private VeterinarioResponseDto veterinario;

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

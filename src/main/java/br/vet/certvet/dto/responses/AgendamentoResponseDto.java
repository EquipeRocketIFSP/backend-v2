package br.vet.certvet.dto.responses;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
import br.vet.certvet.models.Agendamento;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AgendamentoResponseDto extends AgendamentoRequestDto {
    private Long id;
    @JsonProperty("data_consulta")
    private String dataConsulta;
    private AnimalResponseDto animal;
    private UsuarioResponseDto tutor;
    private UsuarioResponseDto veterinario;

    public AgendamentoResponseDto(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.observacoes = agendamento.getObservacoes();
        this.dataConsulta = agendamento.getDataConsulta().toString();
        this.animal = new AnimalResponseDto(agendamento.getAnimal());
        this.tutor = new UsuarioResponseDto(agendamento.getTutor());
        this.veterinario = new UsuarioResponseDto(agendamento.getVeterinario());
    }
}

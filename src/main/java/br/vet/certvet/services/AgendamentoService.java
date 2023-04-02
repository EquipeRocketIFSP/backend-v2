package br.vet.certvet.services;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
import br.vet.certvet.models.Agendamento;
import br.vet.certvet.models.Clinica;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AgendamentoService {
    Agendamento create(AgendamentoRequestDto dto, Clinica clinica);

    Agendamento edit(AgendamentoRequestDto dto, Agendamento agendamento, Clinica clinica);

    Agendamento findOne(Long id);

    List<Agendamento> findAll(LocalDate date, Clinica clinica);

    void delete(Agendamento agendamento);
}

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

    List<Agendamento> findAll(LocalDate date, Clinica clinica);
}

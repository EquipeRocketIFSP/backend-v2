package br.vet.certvet.services;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
import br.vet.certvet.models.Agendamento;
import br.vet.certvet.models.Clinica;
import org.springframework.stereotype.Service;

@Service
public interface AgendamentoService {
    Agendamento create(AgendamentoRequestDto dto, Clinica clinica);
}

package br.vet.certvet.services;

import br.vet.certvet.dto.requests.prontuario.procedimento.ProcedimentoListDTO;
import br.vet.certvet.models.*;

import java.util.List;

public interface ProcedimentoService {
    List<Procedimento> assignToProntuario(ProcedimentoListDTO dto, Clinica clinica, Prontuario prontuario);
}

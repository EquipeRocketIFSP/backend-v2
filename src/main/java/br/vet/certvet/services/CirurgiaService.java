package br.vet.certvet.services;

import br.vet.certvet.dto.requests.prontuario.cirurgia.CirurgiaDTO;
import br.vet.certvet.models.*;

public interface CirurgiaService {
    Cirurgia assignToProntuario(CirurgiaDTO dto, Clinica clinica, Prontuario prontuario);
}

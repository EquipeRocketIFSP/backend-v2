package br.vet.certvet.services;

import br.vet.certvet.dto.requests.prontuario.exame.ExameListDTO;
import br.vet.certvet.models.*;

import java.util.List;

public interface ExameService {
    List<Exame> assignToProntuario(ExameListDTO dto, Prontuario prontuario);
}

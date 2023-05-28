package br.vet.certvet.services;

import br.vet.certvet.models.Procedimento;
import org.springframework.stereotype.Service;
import br.vet.certvet.dto.requests.prontuario.procedimento.ProcedimentoListDTO;
import br.vet.certvet.models.*;
import java.util.*;

@Service
public interface ProcedimentoService {

    Procedimento save(Procedimento procedimento);
    List<Procedimento> assignToProntuario(ProcedimentoListDTO dto, Clinica clinica, Prontuario prontuario);
}

package br.vet.certvet.services;

import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Procedimento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProcedimentoService {
    Procedimento savePrescricao(Procedimento procedimento, List<Prescricao> translate);
}

package br.vet.certvet.services;

import br.vet.certvet.models.Procedimento;
import org.springframework.stereotype.Service;

@Service
public interface ProcedimentoService {

    Procedimento save(Procedimento procedimento);
}

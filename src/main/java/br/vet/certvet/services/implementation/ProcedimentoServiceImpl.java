package br.vet.certvet.services.implementation;

import br.vet.certvet.models.Procedimento;
import br.vet.certvet.repositories.ProcedimentoRepository;
import br.vet.certvet.services.ProcedimentoService;
import org.springframework.stereotype.Service;


@Service
public class ProcedimentoServiceImpl implements ProcedimentoService {

    final private ProcedimentoRepository procedimentoRepository;
    public ProcedimentoServiceImpl(final ProcedimentoRepository procedimentoRepository){
        this.procedimentoRepository = procedimentoRepository;
    }

    @Override
    public Procedimento save(Procedimento procedimento) {
        return procedimentoRepository.save(procedimento);
    }
}

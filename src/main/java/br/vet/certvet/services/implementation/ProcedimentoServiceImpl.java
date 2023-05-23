package br.vet.certvet.services.implementation;

import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Procedimento;
import br.vet.certvet.services.ProcedimentoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedimentoServiceImpl implements ProcedimentoService {

    final private ProcedimentoRepository procedimentoRepository;
    public ProcedimentoServiceImpl(final ProcedimentoRepository procedimentoRepository){
        this.procedimentoRepository = procedimentoRepository;
    }

    @Override
    public Procedimento savePrescricao(Procedimento procedimento, List<Prescricao> translate) {
        translate.forEach(p ->procedimento.getPrescricao().add(p));
        return procedimentoRepository.save(procedimento);
    }

    @Override
    public Procedimento save(Procedimento procedimento) {
        return procedimentoRepository.save(procedimento);
    }
}

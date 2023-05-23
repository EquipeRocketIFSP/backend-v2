package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.prontuario.procedimento.ProcedimentoListDTO;
import br.vet.certvet.models.*;
import br.vet.certvet.models.factories.ProcedimentoFactory;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.ProcedimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProcedimentoServiceImpl implements ProcedimentoService {
    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public List<Procedimento> assignToProntuario(ProcedimentoListDTO dto, Prontuario prontuario) {
        List<Procedimento> procedimentos = dto.getProcedimentos()
                .stream()
                .map((procedimentoDTO) -> ProcedimentoFactory.factory(procedimentoDTO, prontuario))
                .toList();

        prontuario.getProcedimentos().clear();
        prontuario.getProcedimentos().addAll(procedimentos);

        return this.prontuarioRepository.saveAndFlush(prontuario).getProcedimentos();
    }
}

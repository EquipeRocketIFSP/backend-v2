package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.prontuario.procedimento.ProcedimentoListDTO;
import br.vet.certvet.models.*;
import br.vet.certvet.models.factories.ProcedimentoFactory;
import br.vet.certvet.repositories.ProcedimentoRepository;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.EstoqueService;
import br.vet.certvet.services.MedicamentoService;
import br.vet.certvet.services.ProcedimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProcedimentoServiceImpl implements ProcedimentoService {
    final private ProcedimentoRepository procedimentoRepository;
    public ProcedimentoServiceImpl(final ProcedimentoRepository procedimentoRepository){
        this.procedimentoRepository = procedimentoRepository;
    }

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private EstoqueService estoqueService;

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public List<Procedimento> assignToProntuario(ProcedimentoListDTO dto, Clinica clinica, Prontuario prontuario) {
        List<Procedimento> procedimentos = dto.getProcedimentos()
                .stream()
                .map((procedimentoDTO) -> {
                    if (procedimentoDTO.getMedicamento() != null) {
                        final Medicamento medicamento = this.medicamentoService.findOne(procedimentoDTO.getMedicamento(), clinica);
                        final Estoque estoque = this.estoqueService.findOne(procedimentoDTO.getLote(), medicamento);

                        return ProcedimentoFactory.factory(procedimentoDTO, estoque, prontuario);
                    }

                    return ProcedimentoFactory.factory(procedimentoDTO, prontuario);
                }).toList();

        prontuario.getProcedimentos().clear();
        prontuario.getProcedimentos().addAll(procedimentos);

        return this.prontuarioRepository.saveAndFlush(prontuario).getProcedimentos();
    }

    @Override
    public Procedimento save(Procedimento procedimento) {
        return procedimentoRepository.save(procedimento);
    }
}

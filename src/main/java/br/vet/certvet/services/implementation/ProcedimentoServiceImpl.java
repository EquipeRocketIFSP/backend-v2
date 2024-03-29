package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.prontuario.procedimento.ProcedimentoListDTO;
import br.vet.certvet.enums.ProntuarioStatus;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.*;
import br.vet.certvet.models.factories.ProcedimentoFactory;
import br.vet.certvet.repositories.ProcedimentoRepository;
import br.vet.certvet.repositories.ProcedimentoTipoRepository;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.EstoqueService;
import br.vet.certvet.services.MedicamentoService;
import br.vet.certvet.services.ProcedimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProcedimentoServiceImpl implements ProcedimentoService {
    private final ProcedimentoRepository procedimentoRepository;

    public ProcedimentoServiceImpl(final ProcedimentoRepository procedimentoRepository) {
        this.procedimentoRepository = procedimentoRepository;
    }

    @Autowired
    private ProcedimentoTipoRepository procedimentoTipoRepository;

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private EstoqueService estoqueService;

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public List<Procedimento> assignToProntuario(ProcedimentoListDTO dto, Clinica clinica, Prontuario prontuario) {
        if (prontuario.getStatus() == ProntuarioStatus.COMPLETED)
            prontuario.setStatus(ProntuarioStatus.UPDATING);

        List<ProcedimentoTipo> tipos = this.procedimentoTipoRepository.findAll();

        List<Procedimento> procedimentos = dto.getProcedimentos()
                .stream()
                .map(procedimentoDTO -> {
                    final Optional<ProcedimentoTipo> tipo = tipos.stream()
                            .filter((t) -> t.getNome().equals(procedimentoDTO.getProcedimento()))
                            .findFirst();

                    if (tipo.isEmpty())
                        throw new NotFoundException("Procedimento não encontrado");

                    if (procedimentoDTO.getMedicamento() != null && procedimentoDTO.getDose() != null && procedimentoDTO.getLote() != null) {
                        final BigDecimal dose = procedimentoDTO.getDose();
                        final Medicamento medicamento = this.medicamentoService.findOne(procedimentoDTO.getMedicamento(), clinica);
                        final Estoque estoque = this.estoqueService.findOne(procedimentoDTO.getLote(), medicamento);
                        final Procedimento procedimento = ProcedimentoFactory
                                .factory(procedimentoDTO, estoque, prontuario)
                                .setProcedimentoTipo(tipo.get());

                        final String reason = new StringBuilder("Usado no procedimento ")
                                .append(procedimento.getProcedimentoTipo().getNome())
                                .append(" no prontuário ")
                                .append(prontuario.getCodigo())
                                .append(" do animal ")
                                .append(prontuario.getAnimal().getNome()).toString();

                        this.estoqueService.subtract(dose, reason, estoque, prontuario.getVeterinario());

                        return procedimento;
                    }

                    return ProcedimentoFactory.factory(procedimentoDTO, prontuario)
                            .setProcedimentoTipo(tipo.get());
                }).toList();

        prontuario.getProcedimentos().forEach(procedimento -> {
            final Estoque estoque = procedimento.getMedicamentoConsumido();

            if (estoque == null)
                return;

            final BigDecimal dose = procedimento.getDoseMedicamento();
            final String reason = new StringBuilder("Correção no prontuário ").append(prontuario.getCodigo())
                    .append(" do animal ").append(prontuario.getAnimal().getNome()).append(". ")
                    .append("Uso do medicamento foi retificado.")
                    .toString();

            this.estoqueService.add(dose, reason, estoque, prontuario.getVeterinario());
        });

        prontuario.getProcedimentos().clear();
        prontuario.getProcedimentos().addAll(procedimentos);

        return this.prontuarioRepository.saveAndFlush(prontuario).getProcedimentos();
    }

    @Override
    public Procedimento save(Procedimento procedimento) {
        return procedimentoRepository.save(procedimento);
    }
}

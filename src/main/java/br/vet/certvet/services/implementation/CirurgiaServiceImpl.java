package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.prontuario.cirurgia.CirurgiaDTO;
import br.vet.certvet.dto.requests.prontuario.cirurgia.MedicamentoCirurgiaDTO;
import br.vet.certvet.enums.ProntuarioStatus;
import br.vet.certvet.models.*;
import br.vet.certvet.models.factories.CirurgiaFactory;
import br.vet.certvet.models.mappers.CirurgiaDTOMapper;
import br.vet.certvet.repositories.CirurgiaEstoqueMedicamentoRepository;
import br.vet.certvet.repositories.CirurgiaRepository;
import br.vet.certvet.services.CirurgiaService;
import br.vet.certvet.services.EstoqueService;
import br.vet.certvet.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CirurgiaServiceImpl implements CirurgiaService {
    private static final String DO_ANIMAL = " do animal ";
    @Autowired
    private CirurgiaRepository cirurgiaRepository;

    @Autowired
    private CirurgiaEstoqueMedicamentoRepository cirurgiaEstoqueMedicamentoRepository;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private EstoqueService estoqueService;

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Cirurgia assignToProntuario(CirurgiaDTO dto, Clinica clinica, Prontuario prontuario) {
        if (prontuario.getStatus() == ProntuarioStatus.COMPLETED)
            prontuario.setStatus(ProntuarioStatus.UPDATING);

        final Cirurgia cirurgia = prontuario.getCirurgia() != null ?
                CirurgiaDTOMapper.assignToModel(dto, prontuario.getCirurgia()) :
                CirurgiaFactory.factory(dto, prontuario);

        this.removeMedications(dto, cirurgia, prontuario);

        dto.getMedicamentos().forEach(medicamentoCirurgiaDTO -> {
            Optional<CirurgiaEstoqueMedicamento> estoqueMedicamentoOptional = cirurgia.getMedicamentosConsumidos()
                    .stream()
                    .filter(
                            cirurgiaEstoqueMedicamento -> cirurgiaEstoqueMedicamento.getEstoque()
                                    .getId()
                                    .equals(
                                            medicamentoCirurgiaDTO.getLote()))
                    .findFirst();

            final BigDecimal currentDose = medicamentoCirurgiaDTO.getDose();

            if (estoqueMedicamentoOptional.isPresent())
                this.updateMedications(estoqueMedicamentoOptional.get(), currentDose, prontuario);
            else
                this.addMedications(medicamentoCirurgiaDTO, currentDose, cirurgia, prontuario);
        });

        prontuario.setCirurgia(cirurgia);

        return this.cirurgiaRepository.saveAndFlush(cirurgia);
    }

    private void removeMedications(CirurgiaDTO dto, Cirurgia cirurgia, Prontuario prontuario) {
        List<CirurgiaEstoqueMedicamento> medicamentosToRemove = cirurgia.getMedicamentosConsumidos()
                .stream()
                .filter(cirurgiaEstoqueMedicamento -> {
                    boolean notFound = true;

                    for (MedicamentoCirurgiaDTO medicamentoCirurgiaDTO : dto.getMedicamentos()) {
                        if (medicamentoCirurgiaDTO.getLote().equals(cirurgiaEstoqueMedicamento.getEstoque().getId())) {
                            notFound = false;
                            break;
                        }
                    }

                    return notFound;
                }).toList();

        medicamentosToRemove.forEach(cirurgiaEstoqueMedicamento -> {
            final Estoque estoque = cirurgiaEstoqueMedicamento.getEstoque();
            final BigDecimal dose = cirurgiaEstoqueMedicamento.getDose();
            final String reason = new StringBuilder("Correção no prontuário ")
                    .append(prontuario.getCodigo())
                    .append(DO_ANIMAL)
                    .append(
                            prontuario.getAnimal()
                                    .getNome())
                    .append(". Uso do medicamento foi retificado.")
                    .toString();

            this.estoqueService.add(dose, reason, estoque, prontuario.getVeterinario());
        });

        cirurgia.getMedicamentosConsumidos().removeAll(medicamentosToRemove);
    }

    private void updateMedications(CirurgiaEstoqueMedicamento cirurgiaEstoqueMedicamento, BigDecimal currentDose, Prontuario prontuario) {
        final BigDecimal previousDose = cirurgiaEstoqueMedicamento.getDose();
        final Estoque estoque = cirurgiaEstoqueMedicamento.getEstoque();

        if (previousDose.floatValue() == currentDose.floatValue())
            return;

        final String reason = new StringBuilder("Usado na cirurgia ").append(prontuario.getCirurgia().getDescricao())
                .append(" no prontuário ").append(prontuario.getCodigo())
                .append(DO_ANIMAL).append(prontuario.getAnimal().getNome()).append(". ")
                .append("Quantidade atualizada no prontuário: ").append(currentDose).append(" ")
                .append("Quantidade anterior no prontuário: ").append(previousDose).append(". ")
                .toString();

        estoque.setQuantidade(estoque.getQuantidade().add(previousDose));

        cirurgiaEstoqueMedicamento.setDose(currentDose);
        this.estoqueService.subtract(currentDose, reason, estoque, prontuario.getVeterinario());
    }

    private void addMedications(MedicamentoCirurgiaDTO dto, BigDecimal currentDose, Cirurgia cirurgia, Prontuario prontuario) {
        final Medicamento medicamento = this.medicamentoService.findOne(dto.getMedicamento(), prontuario.getClinica());
        final Estoque estoque = this.estoqueService.findOne(dto.getLote(), medicamento);

        final String reason = new StringBuilder("Usado na cirurgia ")
                .append(cirurgia.getDescricao())
                .append(" no prontuário ")
                .append(prontuario.getCodigo())
                .append(DO_ANIMAL)
                .append(prontuario.getAnimal().getNome()).toString();

        final CirurgiaEstoqueMedicamento cirurgiaEstoqueMedicamento = new CirurgiaEstoqueMedicamento()
                .setCirurgia(prontuario.getCirurgia())
                .setEstoque(estoque)
                .setDose(currentDose);

        this.estoqueService.subtract(currentDose, reason, estoque, prontuario.getVeterinario());

        cirurgia.getMedicamentosConsumidos().add(cirurgiaEstoqueMedicamento);
    }
}

package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.prontuario.cirurgia.CirurgiaDTO;
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

import java.sql.SQLException;

@Service
public class CirurgiaServiceImpl implements CirurgiaService {
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
        final Cirurgia cirurgia = prontuario.getCirurgia() != null ?
                CirurgiaDTOMapper.assignToModel(dto, prontuario.getCirurgia()) :
                CirurgiaFactory.factory(dto, prontuario);

        cirurgia.getMedicamentosConsumidos().clear();

        dto.getMedicamentos().forEach((medicamentoCirurgiaDTO) -> {
            final Medicamento medicamento = this.medicamentoService.findOne(medicamentoCirurgiaDTO.getMedicamento(), clinica);
            final Estoque estoque = this.estoqueService.findOne(medicamentoCirurgiaDTO.getLote(), medicamento);
            final CirurgiaEstoqueMedicamento cirurgiaEstoqueMedicamento = new CirurgiaEstoqueMedicamento()
                    .setCirurgia(cirurgia)
                    .setEstoque(estoque)
                    .setDose(medicamentoCirurgiaDTO.getDose());

            cirurgia.getMedicamentosConsumidos().add(cirurgiaEstoqueMedicamento);
        });

        this.cirurgiaRepository.saveAndFlush(cirurgia);

        return cirurgia;
    }
}

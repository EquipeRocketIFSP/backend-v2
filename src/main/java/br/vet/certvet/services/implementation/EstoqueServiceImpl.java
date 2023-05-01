package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.repositories.EstoqueRepository;
import br.vet.certvet.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {
    @Autowired
    private EstoqueRepository estoqueRepository;

    @Override
    public Estoque create(EstoqueRequestDto dto, Medicamento medicamento) {
        Optional<Estoque> response = this.estoqueRepository.findOneByMedicamentoAndLote(medicamento, dto.lote());

        if (response.isPresent())
            throw new ConflictException("Já existe um estoque para esse lote de medicamento");

        Estoque estoque = new Estoque(dto, medicamento);
        return this.estoqueRepository.saveAndFlush(estoque);
    }
}

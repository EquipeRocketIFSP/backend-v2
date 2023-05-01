package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.EstoqueTransacao;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.repositories.EstoqueRepository;
import br.vet.certvet.repositories.EstoqueTransacaoRepository;
import br.vet.certvet.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {
    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EstoqueTransacaoRepository estoqueTransacaoRepository;

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Estoque create(EstoqueRequestDto dto, Medicamento medicamento) {
        Optional<Estoque> response = this.estoqueRepository.findOneByMedicamentoAndLote(medicamento, dto.lote());

        if (response.isPresent())
            throw new ConflictException("Já existe um estoque para esse lote de medicamento");

        final String REASON = "Adição de novo estoque";
        Estoque estoque = new Estoque(dto, medicamento);
        estoque = this.estoqueRepository.saveAndFlush(estoque);

        EstoqueTransacao transacao = new EstoqueTransacao(estoque)
                .setQuantidade(estoque.getQuantidade())
                .setMotivo(REASON);

        this.estoqueTransacaoRepository.saveAndFlush(transacao);

        return estoque;
    }
}

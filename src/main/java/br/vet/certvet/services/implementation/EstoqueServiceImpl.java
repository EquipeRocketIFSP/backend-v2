package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.dto.responses.EstoqueResponseDto;
import br.vet.certvet.dto.responses.Metadata;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.enums.TransacaoStatus;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.ForbiddenException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.EstoqueTransacao;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.EstoqueRepository;
import br.vet.certvet.repositories.EstoqueTransacaoRepository;
import br.vet.certvet.services.EstoqueService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    private EstoqueRepository estoqueRepository;

    private EstoqueTransacaoRepository estoqueTransacaoRepository;

    private static final int RESPONSE_LIMIT = 30;

    public EstoqueServiceImpl (EstoqueRepository estoqueRepository, EstoqueTransacaoRepository estoqueTransacaoRepository){
        this.estoqueRepository = estoqueRepository;
        this.estoqueTransacaoRepository = estoqueTransacaoRepository;
    }


    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Estoque create(EstoqueRequestDto dto, Medicamento medicamento, Usuario responsavel) {
        Optional<Estoque> response = this.estoqueRepository.findOneByMedicamentoAndLote(medicamento, dto.lote());

        if (response.isPresent())
            throw new ConflictException("Já existe um estoque para esse lote de medicamento");

        final String REASON = "Adição de novo estoque";
        Estoque estoque = new Estoque(dto, medicamento);
        estoque = this.estoqueRepository.saveAndFlush(estoque);

        EstoqueTransacao transacao = new EstoqueTransacao(estoque, responsavel)
                .setQuantidade(estoque.getQuantidade())
                .setMotivo(REASON);

        this.estoqueTransacaoRepository.saveAndFlush(transacao);

        return estoque;
    }

    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Estoque edit(EstoqueRequestDto dto, Estoque estoque, Usuario responsavel) {
        Optional<Estoque> response = this.estoqueRepository.findOneByMedicamentoAndLote(estoque.getMedicamento(), dto.lote());

        if (response.isPresent() && !response.get().getId().equals(estoque.getId()))
            throw new ConflictException("Já existe um estoque para esse lote de medicamento");

        final EstoqueTransacao transacao = new EstoqueTransacao(estoque, responsavel);

        String reason;
        BigDecimal quantity;

        if (estoque.getQuantidade().floatValue() > dto.quantidade().floatValue()) {
            reason = "Edição de estoque: Saída no estoque";
            quantity = estoque.getQuantidade().subtract(dto.quantidade());

            transacao.setStatus(TransacaoStatus.EXIT).setQuantidade(quantity).setMotivo(reason);
            this.estoqueTransacaoRepository.saveAndFlush(transacao);
        } else if(estoque.getQuantidade().floatValue() < dto.quantidade().floatValue()) {
            reason = "Edição de estoque: Entrada no estoque";
            quantity = dto.quantidade().subtract(estoque.getQuantidade());

            transacao.setStatus(TransacaoStatus.ENTRY).setQuantidade(quantity).setMotivo(reason);
            this.estoqueTransacaoRepository.saveAndFlush(transacao);
        }

        estoque.fill(dto);

        return this.estoqueRepository.saveAndFlush(estoque);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Estoque add(BigDecimal dose, String reason, Estoque estoque, Usuario responsavel) {
        final EstoqueTransacao transacao = new EstoqueTransacao(estoque, responsavel).setMotivo(reason).setStatus(TransacaoStatus.ENTRY);
        final BigDecimal quantity = estoque.getQuantidade().add(dose);

        estoque.setQuantidade(quantity);
        transacao.setQuantidade(dose);

        this.estoqueTransacaoRepository.saveAndFlush(transacao);
        return this.estoqueRepository.saveAndFlush(estoque);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Estoque subtract(BigDecimal dose, String reason, Estoque estoque, Usuario responsavel) {
        final EstoqueTransacao transacao = new EstoqueTransacao(estoque, responsavel).setMotivo(reason).setStatus(TransacaoStatus.EXIT);
        final BigDecimal quantity = estoque.getQuantidade().subtract(dose);

        if (quantity.floatValue() < 0)
            throw new ForbiddenException("Esse medicamento não possui estoque o suficiente");

        estoque.setQuantidade(quantity);
        transacao.setQuantidade(dose);

        this.estoqueTransacaoRepository.saveAndFlush(transacao);
        return this.estoqueRepository.saveAndFlush(estoque);
    }

    @Override
    public Estoque findOne(Long id, Medicamento medicamento) {
        Optional<Estoque> response = this.estoqueRepository.findOneByMedicamentoAndId(medicamento, id);

        if (response.isEmpty())
            throw new NotFoundException("Estoque não encontrado");

        return response.get();
    }

    @Override
    public PaginatedResponse<EstoqueResponseDto> findAll(int page, String url, Medicamento medicamento) {
        page = Math.max(page, 1);

        final Pageable pageable = PageRequest.of(page - 1, EstoqueServiceImpl.RESPONSE_LIMIT);
        final Long total = this.estoqueRepository.countByMedicamento(medicamento);
        final Metadata metadata = new Metadata(url, page, EstoqueServiceImpl.RESPONSE_LIMIT, total);

        final List<Estoque> estoques = this.estoqueRepository.findAllByMedicamentoOrderByIdDesc(pageable, medicamento);

        final List<EstoqueResponseDto> animalResponseDtos = estoques.stream()
                .map(EstoqueResponseDto::new)
                .toList();

        return new PaginatedResponse<>(metadata, animalResponseDtos);
    }
}

package br.vet.certvet.services;

import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.dto.responses.*;
import br.vet.certvet.models.*;

import java.math.BigDecimal;

public interface EstoqueService {
    Estoque create(EstoqueRequestDto dto, Medicamento medicamento, Usuario responsavel);

    Estoque edit(EstoqueRequestDto dto, Estoque estoque, Usuario responsavel);

    Estoque add(BigDecimal dose, String reason, Estoque estoque, Usuario responsavel);

    Estoque subtract(BigDecimal dose, String reason, Estoque estoque, Usuario responsavel);

    Estoque findOne(Long id, Medicamento medicamento);

    PaginatedResponse<EstoqueResponseDto> findAll(int page, String url, Medicamento medicamento);
}

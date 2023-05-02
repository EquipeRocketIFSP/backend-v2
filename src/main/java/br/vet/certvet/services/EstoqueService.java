package br.vet.certvet.services;

import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.dto.responses.EstoqueResponseDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.Medicamento;

public interface EstoqueService {
    Estoque create(EstoqueRequestDto dto, Medicamento medicamento);

    Estoque edit(EstoqueRequestDto dto, Estoque estoque);

    Estoque findOne(Long id, Medicamento medicamento);

    PaginatedResponse<EstoqueResponseDto> findAll(int page, String url, Medicamento medicamento);
}

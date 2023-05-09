package br.vet.certvet.services;

import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.dto.responses.EstoqueResponseDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.models.Usuario;

public interface EstoqueService {
    Estoque create(EstoqueRequestDto dto, Medicamento medicamento, Usuario responsavel);

    Estoque edit(EstoqueRequestDto dto, Estoque estoque, Usuario responsavel);

    Estoque findOne(Long id, Medicamento medicamento);

    PaginatedResponse<EstoqueResponseDto> findAll(int page, String url, Medicamento medicamento);
}

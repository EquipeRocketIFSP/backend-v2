package br.vet.certvet.services;

import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.Medicamento;

public interface EstoqueService {
    Estoque create(EstoqueRequestDto dto, Medicamento medicamento);
}

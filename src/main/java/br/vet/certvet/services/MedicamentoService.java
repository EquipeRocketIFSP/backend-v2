package br.vet.certvet.services;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.dto.responses.MedicamentoResponseDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.models.Medicamento;
import org.springframework.stereotype.Service;

@Service
public interface MedicamentoService {
    Medicamento create(MedicamentoRequestDto dto);

    Medicamento findOne(Long id);

    PaginatedResponse<MedicamentoResponseDto> findAll(int page, String url);
}

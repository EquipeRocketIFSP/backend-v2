package br.vet.certvet.services;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.models.Medicamento;
import org.springframework.stereotype.Service;

@Service
public interface MedicamentoService {
    Medicamento create(MedicamentoRequestDto dto);

    Medicamento findOne(Long id);
}

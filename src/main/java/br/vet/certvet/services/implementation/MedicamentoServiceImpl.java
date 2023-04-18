package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.dto.responses.MedicamentoResponseDto;
import br.vet.certvet.dto.responses.Metadata;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.repositories.MedicamentoRespository;
import br.vet.certvet.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {
    private static final int RESPONSE_LIMIT = 30;

    @Autowired
    private MedicamentoRespository medicamentoRespository;

    @Override
    public void create(MedicamentoRequestDto dto) {
        Optional<Medicamento> response = this.medicamentoRespository.findByCodigoRegistro(dto.codigoRegistro);

        if (response.isPresent())
            throw new ConflictException("Medicamento já existe");
    }

    @Override
    public Medicamento findOne(Long id) {
        Optional<Medicamento> response = this.medicamentoRespository.findById(id);

        if (response.isEmpty())
            throw new NotFoundException("Medicamento não encontrado");

        return response.get();
    }

    @Override
    public PaginatedResponse<MedicamentoResponseDto> findAll(int page, String url) {
        Long total = this.medicamentoRespository.count();
        Metadata metadata = new Metadata(url, page, MedicamentoServiceImpl.RESPONSE_LIMIT, total);
        Pageable pageable = PageRequest.of(page - 1, MedicamentoServiceImpl.RESPONSE_LIMIT);

        List<MedicamentoResponseDto> medicamentoResponseDtos = this.medicamentoRespository.findAll(pageable)
                .stream()
                .map((medicamento) -> new MedicamentoResponseDto(medicamento))
                .toList();

        return new PaginatedResponse<>(metadata, medicamentoResponseDtos);
    }
}

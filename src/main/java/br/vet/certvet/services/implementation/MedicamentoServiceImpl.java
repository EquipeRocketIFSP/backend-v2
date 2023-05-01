package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.dto.responses.MedicamentoResponseDto;
import br.vet.certvet.dto.responses.Metadata;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Clinica;
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
    public Medicamento create(MedicamentoRequestDto dto, Clinica clinica) {
        Optional<Medicamento> response = this.medicamentoRespository.findByCodigoRegistroAndClinica(
                dto.codigoRegistro(),
                clinica
        );

        if (response.isPresent())
            throw new ConflictException("Medicamento já existe");

        Medicamento medicamento = new Medicamento(dto, clinica);

        return this.medicamentoRespository.saveAndFlush(medicamento);
    }

    @Override
    public Medicamento edit(MedicamentoRequestDto dto, Medicamento medicamento) {
        medicamento.fill(dto);

        return this.medicamentoRespository.saveAndFlush(medicamento);
    }

    @Override
    public Medicamento findOne(Long id) {
        Optional<Medicamento> response = this.medicamentoRespository.findById(id);

        if (response.isEmpty())
            throw new NotFoundException("Medicamento não encontrado");

        return response.get();
    }

    @Override
    public PaginatedResponse<MedicamentoResponseDto> findAll(int page, String search, String url) {
        page = Math.max(page, 1);

        Pageable pageable = PageRequest.of(page - 1, MedicamentoServiceImpl.RESPONSE_LIMIT);

        Long total = search.trim().isEmpty() ?
                this.medicamentoRespository.count() :
                this.medicamentoRespository.countBySearchedParams(search);

        Metadata metadata = new Metadata(url, page, MedicamentoServiceImpl.RESPONSE_LIMIT, total);

        List<Medicamento> medicamentos = search.trim().isEmpty() ?
                this.medicamentoRespository.findAll(pageable).toList() :
                this.medicamentoRespository.searchByNomeAndNomeReferencia(pageable, search);

        List<MedicamentoResponseDto> medicamentoResponseDtos = medicamentos.stream()
                .map(MedicamentoResponseDto::factory)
                .toList();

        return new PaginatedResponse<>(metadata, medicamentoResponseDtos);
    }
}
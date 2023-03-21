package br.vet.certvet.services;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.ClinicaRequestDto;
import br.vet.certvet.models.Clinica;
import org.springframework.stereotype.Service;

@Service
public interface ClinicaService {
    Clinica create(ClinicaInicialRequestDto dto);

    Clinica findById(Long id);

    Clinica findByCnpj(String cnpj);

    Clinica findOne(String code);

    Clinica edit(ClinicaRequestDto dto, Clinica clinica);
}

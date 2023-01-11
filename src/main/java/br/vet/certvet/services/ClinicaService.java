package br.vet.certvet.services;

import br.vet.certvet.dto.request.ClinicaInicialRequestDto;
import br.vet.certvet.model.Clinica;
import org.springframework.stereotype.Service;

@Service
public interface ClinicaService {
    Clinica criar(ClinicaInicialRequestDto dto);
}

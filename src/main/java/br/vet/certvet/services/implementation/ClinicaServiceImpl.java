package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.request.ClinicaInicialRequestDto;
import br.vet.certvet.model.Clinica;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.services.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicaServiceImpl implements ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    public Clinica criar(ClinicaInicialRequestDto dto) {
        Clinica clinica = new Clinica(dto);

        return this.clinicaRepository.saveAndFlush(clinica);
    }
}

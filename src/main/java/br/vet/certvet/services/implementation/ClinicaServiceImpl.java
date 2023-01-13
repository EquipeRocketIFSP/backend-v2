package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.exceptions.ConfilctException;
import br.vet.certvet.model.Clinica;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.services.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClinicaServiceImpl implements ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    public Clinica criar(ClinicaInicialRequestDto dto) {
        Optional<Clinica> response = this.clinicaRepository.findByCnpj(dto.clinica_cnpj);

        if (response.isPresent())
            throw new ConfilctException("Clínica já existe.");

        return this.clinicaRepository.saveAndFlush(new Clinica(dto));
    }
}

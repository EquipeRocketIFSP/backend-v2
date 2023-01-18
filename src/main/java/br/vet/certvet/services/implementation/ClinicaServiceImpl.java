package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Clinica;
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
            throw new ConflictException("Clínica já existe.");

        return this.clinicaRepository.saveAndFlush(new Clinica(dto));
    }

    @Override
    public Clinica recuperar(Long id) {
        Optional<Clinica> response = this.clinicaRepository.findById(id);

        if(response.isEmpty())
            throw new NotFoundException("Clínica não existe.");

        return response.get();
    }
}

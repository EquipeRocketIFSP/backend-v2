package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.repositories.MedicamentoRespository;
import br.vet.certvet.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {
    @Autowired
    private MedicamentoRespository medicamentoRespository;

    @Override
    public Medicamento create(MedicamentoRequestDto dto) {
        Optional<Medicamento> response = this.medicamentoRespository.findByCodigoRegistro(dto.codigoRegistro);

        if (response.isPresent())
            throw new ConflictException("Medicamento já existe");

        Medicamento medicamento = new Medicamento(dto);

        return this.medicamentoRespository.saveAndFlush(medicamento);
    }

    @Override
    public Medicamento findOne(Long id) {
        Optional<Medicamento> response = this.medicamentoRespository.findById(id);

        if (response.isEmpty())
            throw new NotFoundException("Medicamento não encontrado");

        return response.get();
    }
}

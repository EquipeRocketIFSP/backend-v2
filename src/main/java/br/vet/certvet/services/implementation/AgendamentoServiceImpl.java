package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.models.Agendamento;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.AgendamentoRepository;
import br.vet.certvet.services.AgendamentoService;
import br.vet.certvet.services.AnimalService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Agendamento create(AgendamentoRequestDto dto, Clinica clinica) {
        Animal animal = this.animalService.findOne(dto.animal);
        Usuario tutor = this.usuarioService.findOne(dto.tutor, clinica);
        Usuario veterinario = this.usuarioService.findOne(dto.veterinario, clinica);

        LocalDateTime dataInicial = dto.data_consulta.withMinute(0), dataFinal = dataInicial.plusHours(1);

        Optional<Agendamento> response = this.agendamentoRepository.findByAnimalAndDataConsultaBetween(animal, dataInicial, dataFinal);

        if (response.isPresent())
            throw new ConflictException("Já existe um agendamento para esse animal nesse horário");

        response = this.agendamentoRepository.findByVeterinarioAndDataConsultaBetween(veterinario, dataInicial, dataFinal);

        if (response.isPresent())
            throw new ConflictException("Já existe um agendamento para esse veterinário nesse horário");

        Agendamento agendamento = new Agendamento(dto, veterinario, animal, tutor, clinica);

        return this.agendamentoRepository.saveAndFlush(agendamento);
    }
}

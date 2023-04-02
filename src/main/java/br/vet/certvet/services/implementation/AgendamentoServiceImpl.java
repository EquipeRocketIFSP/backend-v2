package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.NotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
        Animal animal = this.animalService.findOne(dto.getAnimal());
        Usuario tutor = this.usuarioService.findOne(dto.getTutor(), clinica);
        Usuario veterinario = this.usuarioService.findOneVeterinario(dto.getVeterinario(), clinica);

        this.checkForScheduledAgendamento(dto, animal, veterinario);

        Agendamento agendamento = new Agendamento(dto, veterinario, animal, tutor, clinica);

        return this.agendamentoRepository.saveAndFlush(agendamento);
    }

    @Override
    @Transactional
    public Agendamento edit(AgendamentoRequestDto dto, Agendamento agendamento, Clinica clinica) {
        this.delete(agendamento);
        return this.create(dto, clinica);
    }

    @Override
    public Agendamento findOne(Long id) {
        Optional<Agendamento> response = this.agendamentoRepository.findById(id);

        if (response.isEmpty())
            throw new NotFoundException("Agendamento não encontrado");

        return response.get();
    }

    @Override
    public List<Agendamento> findAll(LocalDate date, Clinica clinica) {
        date = date != null ? date : LocalDate.now();

        LocalTime defaultTime = LocalTime.parse("00:00:00");

        LocalDateTime start = LocalDateTime.of(date.withDayOfMonth(1), defaultTime);
        LocalDateTime end = LocalDateTime.of(date.withDayOfMonth(date.lengthOfMonth()), defaultTime);

        return this.agendamentoRepository.findAllByClinicaAndDataConsultaBetween(clinica, start, end);
    }

    @Override
    public void delete(Agendamento agendamento) {
        this.agendamentoRepository.delete(agendamento);
    }

    private void checkForScheduledAgendamento(AgendamentoRequestDto dto, Animal animal, Usuario veterinario) throws ConflictException {
        LocalDateTime dataInicial = dto.getDataConsulta().withMinute(0), dataFinal = dataInicial.plusHours(1);

        Optional<Agendamento> response = this.agendamentoRepository.findByAnimalAndDataConsultaBetween(animal, dataInicial, dataFinal);

        if (response.isPresent())
            throw new ConflictException("Já existe um agendamento para esse animal nesse horário");

        response = this.agendamentoRepository.findByVeterinarioAndDataConsultaBetween(veterinario, dataInicial, dataFinal);

        if (response.isPresent())
            throw new ConflictException("Já existe um agendamento para esse veterinário nesse horário");
    }
}

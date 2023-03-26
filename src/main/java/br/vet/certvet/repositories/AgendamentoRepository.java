package br.vet.certvet.repositories;

import br.vet.certvet.models.Agendamento;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Optional<Agendamento> findByAnimalAndDataConsultaBetween(Animal animal, LocalDateTime dataInicial, LocalDateTime dataFinal);
    Optional<Agendamento> findByVeterinarioAndDataConsultaBetween(Usuario veterinario, LocalDateTime dataInicial, LocalDateTime dataFinal);
}

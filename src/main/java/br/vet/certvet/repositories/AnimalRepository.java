package br.vet.certvet.repositories;

import br.vet.certvet.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findById(Long id);
}

package br.vet.certvet.repositories;

import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    Optional<Prontuario> findOneByIdAndAnimal(Long id, Animal animal);
}
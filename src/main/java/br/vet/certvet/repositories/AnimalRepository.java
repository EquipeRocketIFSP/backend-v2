package br.vet.certvet.repositories;

import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findById(Long id);

    List<Animal> findAllByTutores(Pageable pageable, Usuario tutor);

    List<Animal> findAllByNomeContainingAndTutores(Pageable pageable, String search, Usuario tutor);

    Long countByTutores(Usuario tutor);

    Long countByNomeContainingAndTutores(String search, Usuario tutor);
}

package br.vet.certvet.repositories;

import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findOneByIdAndTutores(Long id, Usuario tutores);

    List<Animal> findAllByTutores(Pageable pageable, Usuario tutor);

    List<Animal> findAllByNomeContainingAndTutores(Pageable pageable, String search, Usuario tutor);

    Long countByTutores(Usuario tutor);

    Long countByNomeContainingAndTutores(String search, Usuario tutor);

    @Query("select a from Animal a inner join a.tutores tutores where tutores.id = ?1 and a.nome = ?2")
    Optional<Animal> findByTutoridAndNome(Long id, String nome);
}

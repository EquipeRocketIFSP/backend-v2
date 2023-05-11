package br.vet.certvet.repositories;

import br.vet.certvet.models.Cirurgia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirurgiaRepository extends JpaRepository<Cirurgia, Long> {
}
package br.vet.certvet.repositories;

import br.vet.certvet.models.Musculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusculoRepository extends JpaRepository<Musculo, Long> {
}

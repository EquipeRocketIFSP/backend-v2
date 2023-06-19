package br.vet.certvet.repositories;

import br.vet.certvet.models.Prescricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescricaoRepository extends JpaRepository<Prescricao, Long> {
}
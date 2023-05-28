package br.vet.certvet.repositories;

import br.vet.certvet.models.CirurgiaEstoqueMedicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CirurgiaEstoqueMedicamentoRepository extends JpaRepository<CirurgiaEstoqueMedicamento, Long> {
}

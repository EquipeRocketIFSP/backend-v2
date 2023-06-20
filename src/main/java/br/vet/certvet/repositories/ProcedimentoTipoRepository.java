package br.vet.certvet.repositories;

import br.vet.certvet.models.ProcedimentoTipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoTipoRepository extends JpaRepository<ProcedimentoTipo, Long> {
}

package br.vet.certvet.repositories;

import br.vet.certvet.models.ProcedimentoTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedimentoTipoRepository extends JpaRepository<ProcedimentoTipo, Long> {
}

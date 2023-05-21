package br.vet.certvet.services.implementation;

import br.vet.certvet.models.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
}
package br.vet.certvet.repositories;

import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.Medicamento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findOneByMedicamentoAndLote(Medicamento medicamento, String lote);

    Optional<Estoque> findOneByMedicamentoAndId(Medicamento medicamento, Long id);

    List<Estoque> findAllByMedicamentoOrderByIdDesc(Pageable pageable, Medicamento medicamento);

    Long countByMedicamento(Medicamento medicamento);
}

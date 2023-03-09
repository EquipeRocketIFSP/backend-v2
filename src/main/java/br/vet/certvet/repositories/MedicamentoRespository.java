package br.vet.certvet.repositories;

import br.vet.certvet.models.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicamentoRespository extends JpaRepository<Medicamento, Long> {
    Optional<Medicamento> findByCodigoRegistro(String codigoRegistro);
}

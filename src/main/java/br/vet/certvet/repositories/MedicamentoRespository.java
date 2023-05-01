package br.vet.certvet.repositories;

import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Medicamento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedicamentoRespository extends JpaRepository<Medicamento, Long> {
    Optional<Medicamento> findByCodigoRegistroAndClinica(String codigoRegistro, Clinica clinica);

    @Query("SELECT m FROM Medicamento m WHERE m.nome LIKE :search%")
    List<Medicamento> searchByNomeAndNomeReferencia(Pageable pageable, String search);

    @Query("SELECT COUNT(m.id) FROM Medicamento m WHERE m.nome LIKE :search%")
    Long countBySearchedParams(String search);
}

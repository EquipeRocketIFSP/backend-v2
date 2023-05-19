package br.vet.certvet.repositories;

import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Medicamento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicamentoRespository extends JpaRepository<Medicamento, Long> {
    Optional<Medicamento> findByIdAndClinica(Long id, Clinica clinica);

    Optional<Medicamento> findByCodigoRegistroAndClinica(String codigoRegistro, Clinica clinica);

    List<Medicamento> findAllByClinica(Pageable pageable, Clinica clinica);

    List<Medicamento> findAllByClinicaAndNome(Pageable pageable, Clinica clinica, String nome);

    Long countByClinica(Clinica clinica);

    Long countByClinicaAndNomeContains(Clinica clinica, String nome);
}

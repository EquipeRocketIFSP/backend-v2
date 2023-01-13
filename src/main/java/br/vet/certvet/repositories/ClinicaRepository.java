package br.vet.certvet.repositories;

import br.vet.certvet.model.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    Optional<Clinica> findByCnpj(String cnpj);
}

package br.vet.certvet.repositories;

import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByCpfAndClinica(String cpf, Clinica clinica);

    Optional<Usuario> findByIdAndClinica(Long id, Clinica clinicaFromRequester);
}
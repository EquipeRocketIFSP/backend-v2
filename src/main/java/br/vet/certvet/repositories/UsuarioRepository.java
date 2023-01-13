package br.vet.certvet.repositories;

import br.vet.certvet.model.Clinica;
import br.vet.certvet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsernameAndClinica(String username, Clinica clinica);
}

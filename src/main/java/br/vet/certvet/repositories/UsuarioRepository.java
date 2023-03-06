package br.vet.certvet.repositories;

import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByIdAndClinica(Long id, Clinica clinica);

    Optional<Usuario> findByUsernameAndClinica(String username, Clinica clinica);

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findAllByUsername(String username);

    List<Usuario> findAllByAuthoritiesAndClinica(Pageable pageable, Authority authority, Clinica clinica);

    Long countByAuthoritiesAndClinica(Authority authority, Clinica clinica);
}

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

    Optional<Usuario> findByResetPasswordToken(String token);

    List<Usuario> findAllByUsername(String username);

    List<Usuario> findAllByAuthoritiesAndClinica(Pageable pageable, Authority authority, Clinica clinica);

    List<Usuario> findAllByNomeContainingAndAuthoritiesAndClinica(Pageable pageable, String search, Authority authority, Clinica clinica);

    Long countByAuthoritiesAndClinica(Authority authority, Clinica clinica);

    Long countByNomeContainingAndAuthoritiesAndClinica(String search, Authority authority, Clinica clinica);

    Optional<Usuario> findByCpf(String signerCpf);
}

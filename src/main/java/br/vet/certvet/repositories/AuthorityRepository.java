package br.vet.certvet.repositories;

import br.vet.certvet.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority getByAuthority(String authority);
}

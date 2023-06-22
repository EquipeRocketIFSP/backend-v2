package br.vet.certvet.repositories;

import br.vet.certvet.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ApetiteRepository extends JpaRepository<ApetiteModel, Long> {
}

package br.vet.certvet.repositories;

import br.vet.certvet.models.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    @Query("select d from Documento d where d.codigo = ?1")
    Optional<Documento> findByCodigo(String documentoCodigo);
}
package br.vet.certvet.repositories;

import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    List<Documento> findByIdAndDocumentos_tipo(Long prontuarioId, String tipo);

    boolean existsByDataAtendimento(LocalDateTime dataAtendimento);

    List<Prontuario> findAllByCodigo(String codigo);

    Optional<Prontuario> findByCodigo(String prontuarioCodigo);
}
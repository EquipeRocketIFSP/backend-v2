package br.vet.certvet.repositories;

import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    List<Documento> findByIdAndDocumentos_tipo(Long prontuarioId, String tipo);
}
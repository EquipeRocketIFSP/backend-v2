package br.vet.certvet.repositories;

import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    Optional<Prontuario> findOneByIdAndAnimal(Long id, Animal animal);

    @Query("select p from Prontuario p inner join p.documentos documentos where p.id = ?1 and documentos.tipo = ?2")
    List<Documento> findAllByIdAndDocumentoTipo(Long prontuarioId, String tipo);

    boolean existsByDataAtendimento(LocalDateTime dataAtendimento);

    @Query("select p from Prontuario p where p.codigo = ?1")
    Optional<Prontuario> findByCodigo(String prontuarioCodigo);

    @Query("select p from Prontuario p inner join p.documentos documentos where p.codigo = ?1 and documentos.tipo = ?2")
    List<Documento> findAllByCodigoAndDocumentoTipo(String prontuarioCodigo, String tipo);

    Long countByAnimal(Animal animal);

    Long countByAnimalAndCodigoContains(Animal animal, String codigo);

    List<Prontuario> findAllByAnimal(Pageable pageable, Animal animal);

    List<Prontuario> findAllByAnimalAndCodigoContains(Pageable pageable, Animal animal, String codigo);
}
package br.vet.certvet.repositories;

import br.vet.certvet.models.TipoExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoExameRepository extends JpaRepository<TipoExame,Long> {
    @Query("SELECT t FROM TipoExame t WHERE t.pai IS NULL")
    List<TipoExame> findAllParent();
}

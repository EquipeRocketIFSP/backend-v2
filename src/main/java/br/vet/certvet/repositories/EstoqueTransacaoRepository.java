package br.vet.certvet.repositories;

import br.vet.certvet.models.EstoqueTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueTransacaoRepository extends JpaRepository<EstoqueTransacao, Long> {
}

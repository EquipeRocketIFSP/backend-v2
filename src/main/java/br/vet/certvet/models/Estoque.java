package br.vet.certvet.models;

import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.models.contracts.Fillable;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Table(name = "Estoque")
@Accessors(chain = true)
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estoque implements Fillable<EstoqueRequestDto> {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private BigDecimal quantidade;

    @Column(nullable = false)
    private String medida;

    @Column(nullable = false)
    private String lote;

    @Column(nullable = false)
    private LocalDate validade;

    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    @OneToMany(mappedBy = "medicamentoConsumido")
    private List<Procedimento> procedimentos;

    @ManyToOne
    @JoinColumn(name = "cirurgia_id")
    private Cirurgia cirurgia;

    @OneToMany(mappedBy = "estoque")
    @ToString.Exclude
    private List<EstoqueTransacao> transacoes;

    public void setTransacoes(List<EstoqueTransacao> transacoes) {
        this.transacoes = transacoes;
    }

    public Estoque(EstoqueRequestDto dto, Medicamento medicamento) {
        this.fill(dto);
        this.medicamento = medicamento;
    }

    @Override
    public void fill(EstoqueRequestDto dto) {
        this.quantidade = dto.quantidade();
        this.lote = dto.lote();
        this.validade = dto.validade();
        this.medida = dto.medida();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Estoque estoque = (Estoque) o;
        return id != null && Objects.equals(id, estoque.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package br.vet.certvet.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Table(name = "Estoque")
@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {

    @Id
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

    @ManyToOne
    @JoinColumn(name = "procedimento_id")
    private Procedimento procedimento;

    @ManyToOne
    @JoinColumn(name = "cirurgia_id")
    private Cirurgia cirurgia;

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

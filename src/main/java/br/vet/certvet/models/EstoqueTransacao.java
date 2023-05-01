package br.vet.certvet.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table
@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueTransacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Setter
    @Accessors(chain = true)
    @Column(nullable = false)
    private boolean status = false;

    @Setter
    @Accessors(chain = true)
    @Column(nullable = false)
    private BigDecimal quantidade;

    @Setter
    @Accessors(chain = true)
    private String motivo;

    @CreationTimestamp
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    public EstoqueTransacao(Estoque estoque) {
        this.estoque = estoque;
    }
}
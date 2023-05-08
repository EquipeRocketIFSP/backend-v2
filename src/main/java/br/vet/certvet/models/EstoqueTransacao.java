package br.vet.certvet.models;

import br.vet.certvet.enums.TransacaoStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    @OneToOne
    private Usuario responsavel;

    public EstoqueTransacao(Estoque estoque, Usuario responsavel) {
        this.estoque = estoque;
        this.responsavel = responsavel;
    }

    public EstoqueTransacao setStatus(TransacaoStatus status) {
        this.status = status.getStatus();
        return this;
    }
}

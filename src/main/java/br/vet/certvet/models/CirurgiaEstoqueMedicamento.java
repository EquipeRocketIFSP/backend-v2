package br.vet.certvet.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Accessors(chain = true)
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CirurgiaEstoqueMedicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cirurgia_id")
    private Cirurgia cirurgia;

    private BigDecimal dose;
}

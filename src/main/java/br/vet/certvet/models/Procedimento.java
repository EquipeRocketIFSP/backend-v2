package br.vet.certvet.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "procedimentos")
public class Procedimento {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String descricao;
    private String outros;

    @ManyToOne
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;

    @OneToOne
    @JoinColumn(name = "estoque_id")
    private Estoque medicamentoConsumido;

    private BigDecimal doseMedicamento;

    public Procedimento(Prontuario prontuario) {
        this.prontuario = prontuario;
    }
}
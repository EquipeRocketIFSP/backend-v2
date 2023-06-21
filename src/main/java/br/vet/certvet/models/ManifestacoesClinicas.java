package br.vet.certvet.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "manifestacoes_clinicas")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class ManifestacoesClinicas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TINYINT")
    private boolean prostracao;
    @Column(columnDefinition = "TINYINT")
    private boolean febre;
    @Column(columnDefinition = "TINYINT")
    private boolean vomito;
    @Column(columnDefinition = "TINYINT")
    private boolean diarreia;
    @Column(columnDefinition = "TINYINT")
    private boolean espasmosConvulsao;
    @Column(columnDefinition = "TINYINT")
    private boolean deambulacao;
    @Column(columnDefinition = "TINYINT")
    private boolean sensibilidadeDor;
    @Column(columnDefinition = "TINYINT")
    private boolean lesoesNodulos;
    @Column(columnDefinition = "TINYINT")
    private boolean regiaoCabeca;
    @Column(columnDefinition = "TINYINT")
    private boolean regiaoTorax;

    private String regioesObs;

    @ManyToOne
    @JoinTable(name = "manifestacoes_apetite",
            joinColumns = @JoinColumn(name = "manifestacoes_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "apetite_id", referencedColumnName = "id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"manifestacoes_id", "apetite_id"}) }
    )
    private ApetiteModel apetite;

    @OneToOne
    private Prontuario prontuario;

}

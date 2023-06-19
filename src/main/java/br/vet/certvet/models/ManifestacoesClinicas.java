package br.vet.certvet.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "manifestacoes_clinicas")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    private String regiaoColuna;
    private String regiaoAbdomen;
    private String regiaoMToracicos;
    private String regiaoMPelvicos;
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

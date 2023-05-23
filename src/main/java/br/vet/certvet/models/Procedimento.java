package br.vet.certvet.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "procedimento")
    private List<Estoque> medicamentosConsumidos;

    public Procedimento(Prontuario prontuario) {
        this.prontuario = prontuario;
    }
}
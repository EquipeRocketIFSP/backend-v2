package br.vet.certvet.models;

import br.vet.certvet.models.manifestacoes_clinicas.LinfonodosEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "linfonodos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Linfonodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private LinfonodosEnum linfonodo;

    @ManyToOne
    @ToString.Exclude
    @JoinTable(
            name = "prontuario_linfonodos",
            joinColumns = @JoinColumn(name = "prontuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "linfonodo_id", referencedColumnName = "id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"prontuario_id", "linfonodo_id"}) }
    )
    private Prontuario prontuario;

}

package br.vet.certvet.models;

import br.vet.certvet.models.manifestacoes_clinicas.LinfonodosEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(unique = true)
    private String linfonodo;

    @ManyToMany(mappedBy = "linfonodos")
    @ToString.Exclude
    private List<Prontuario> prontuarios;

}

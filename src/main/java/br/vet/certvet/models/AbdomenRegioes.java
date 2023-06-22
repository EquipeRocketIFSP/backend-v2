package br.vet.certvet.models;

import br.vet.certvet.models.manifestacoes_clinicas.AbdomenRegioesEnum;
import br.vet.certvet.models.manifestacoes_clinicas.ColunaRegioesEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "abdomen_regioes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AbdomenRegioes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nome;

    @ManyToMany(mappedBy = "abdomenRegioes")
    @ToString.Exclude
    private List<Prontuario> prontuarios;

}

package br.vet.certvet.models;

import br.vet.certvet.models.manifestacoes_clinicas.MusculosEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "musculos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Musculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nome;

    @ManyToMany(mappedBy = "musculos")
    @ToString.Exclude
    private List<Prontuario> prontuarios;

}

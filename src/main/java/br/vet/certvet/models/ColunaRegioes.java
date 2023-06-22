package br.vet.certvet.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "coluna_regioes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ColunaRegioes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nome;


    @ManyToMany(mappedBy = "colunaRegioes")
    @ToString.Exclude
    private List<Prontuario> prontuarios;

}

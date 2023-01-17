package br.vet.certvet.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "procedimento")
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String descricao;

    @ManyToOne
    private Prontuario prontuario;

    @OneToMany(mappedBy = "procedimento")
    private List<Estoque> medicamentosConsumidos;

}
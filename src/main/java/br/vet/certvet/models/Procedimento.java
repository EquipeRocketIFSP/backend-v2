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
    @ToString.Exclude
    private List<Estoque> medicamentosConsumidos;

    @OneToMany
    @JoinTable(
            name = "procedimento_prescricoes",
            joinColumns = @JoinColumn(name = "procedimento_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "prescricao_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private List<Prescricao> prescricao;

}
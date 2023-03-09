package br.vet.certvet.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class ParentescoAnimal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @OneToOne(optional = false)
    @JoinColumn(name = "parente_id", nullable = false)
    private Animal parente;

    @OneToOne
    @JoinColumn(name = "grau_parentesco", nullable = false)
    private Parentesco grau;
}

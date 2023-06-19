package br.vet.certvet.models;

import br.vet.certvet.enums.SexoAnimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sexo_animal")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SexoAnimalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TINYINT")
    private Boolean bitValue;

    @Enumerated(EnumType.STRING)
    private SexoAnimal genero;
}

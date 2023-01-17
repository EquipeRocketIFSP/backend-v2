package br.vet.certvet.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Transient
    private static final String MASCULINO = "MASCULINO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    private List<ParentescoAnimal> parentescos;

    private String nome;

    @NotBlank
    private String sexo;

    @ManyToMany
    @JoinTable(name = "animal_tutores",
            joinColumns = @JoinColumn(name = "animal_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tutor_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private List<Usuario> tutores;


//    /**
//     * Sempre a necessidade de invocar o método pelo menos duas vezes,
//     * uma para cada elemento.
//     * Cada animal pode ter seus pais, assim como os pais têm seus filhos.
//     * O ideal é que apenas um nivel para cima ou para baixo sejam registradas,
//     * pois podem ser encontradas navegando pelas entidades registradas.
//     *
//     * @param animal
//     * @param grau
//     * @return this
//     */
//    public Animal addParentesco(Animal animal, GrauParentesco grau){
//        parentescos.add(
//                Parentesco.builder()
//                        .animal(this)
//                        .parente(animal)
//                        .grau(grau)
//                        .build()
//        );
//        return this;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Animal animal = (Animal) o;
        return id != null && Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

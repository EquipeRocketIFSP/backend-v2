package br.vet.certvet.models;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.enums.SexoAnimal;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Setter
    @Column(nullable = false)
    private String nome;

    @Setter
    @Column(nullable = false)
    private int idade;

    @Setter
    @Column(nullable = false)
    private String raca;

    @Setter
    @Column(nullable = false)
    private String especie;

    @Setter
    @Column(nullable = false)
    private String pelagem;

    @Setter
    @Column(nullable = false)
    private SexoAnimal sexo;

    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    private List<ParentescoAnimal> parentescos;

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

    public Animal(AnimalRequestDto dto) {

        System.out.println(dto.sexo);

        this.nome = dto.nome;
        this.especie = dto.especie;
        this.sexo = SexoAnimal.valueOf(dto.sexo);
        this.idade = dto.idade;
        this.pelagem = dto.pelagem;
        this.raca = dto.raca;

        this.tutores = new ArrayList<>();
        this.parentescos = new ArrayList<>();
    }
}

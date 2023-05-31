package br.vet.certvet.models;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.enums.SexoAnimal;
import br.vet.certvet.models.contracts.Fillable;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal implements Fillable<AnimalRequestDto> {
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
    private int anoNascimento;

    @Setter
    @Column(nullable = false)
    private float peso;

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

    private String formaIdentificacao;

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

    public void setTutores(List<Usuario> tutores) {
        this.tutores = tutores;
    }

    public void setParentescos(List<ParentescoAnimal> parentescos) {
        this.parentescos = parentescos;
    }


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
        this.tutores = new ArrayList<>();
        this.parentescos = new ArrayList<>();

        this.fill(dto);
    }

    @Override
    public void fill(AnimalRequestDto dto) {
        this.nome = dto.getNome();
        this.especie = dto.getEspecie();
        this.sexo = SexoAnimal.valueOf(dto.getSexo());
        this.anoNascimento = dto.getAno_nascimento();
        this.peso = dto.getPeso();
        this.pelagem = dto.getPelagem();
        this.raca = dto.getRaca();
    }

    public int getIdade() {
        return Period.between(
                LocalDate.of(anoNascimento, Month.of(1), 1),
                LocalDate.now()
        ).getYears();
    }
}

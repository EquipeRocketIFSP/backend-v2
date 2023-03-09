package br.vet.certvet.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Usuario usuario;

    @ManyToMany(mappedBy = "tutores")
    @ToString.Exclude
    private List<Animal> animais;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tutor tutor = (Tutor) o;
        return id != null && Objects.equals(id, tutor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

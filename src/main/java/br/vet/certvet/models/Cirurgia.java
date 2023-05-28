package br.vet.certvet.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Accessors(chain = true)
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cirurgia {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime data;

    @OneToOne
    private Prontuario prontuario;

    @OneToMany(mappedBy = "cirurgia", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<CirurgiaEstoqueMedicamento> medicamentosConsumidos = new ArrayList<>();

    public Cirurgia(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cirurgia cirurgia = (Cirurgia) o;
        return id != null && Objects.equals(id, cirurgia.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

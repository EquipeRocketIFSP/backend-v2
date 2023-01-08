package br.vet.certvet.model;

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
public class Cirurgia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    private TipoCirugia tipo;

    @OneToOne
    private Prontuario prontuario;

    @OneToMany(mappedBy = "cirurgia")
    @ToString.Exclude
    private List<Estoque> medicamentosConsumidos;

//    @ManyToMany
//    @JoinTable(name = "cirurgia_medicamentos",
//            joinColumns = @JoinColumn(name = "cirurgia_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "medicamentos_id", referencedColumnName = "id")
//    )
//    @ToString.Exclude
//    private List<Medicamento> medicamentos;

    public void setMedicamentosConsumidos(List<Estoque> medicamentosConsumidos) {
        this.medicamentosConsumidos = medicamentosConsumidos;
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

package br.vet.certvet.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class Clinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String razaoSocial;

    @OneToMany
    @JoinTable(name = "clinica_usuarios",
            joinColumns = @JoinColumn(name = "clinica_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Usuario> usuarios;

    @OneToMany
    @ToString.Exclude
    private List<Agendamento> agendamentos;

//    @OneToMany
//    @ToString.Exclude
//    private List<Estoque> estoque;

    @OneToMany
    @ToString.Exclude
    private List<Prontuario> prontuarios;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Clinica clinica = (Clinica) o;
        return id != null && Objects.equals(id, clinica.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

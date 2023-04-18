package br.vet.certvet.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String codigoRegistro;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String principioAtivo;

    @Column(nullable = false)
    private String viaUso;

    @Column(nullable = false)
    private String concentracao;

    @Column(nullable = false)
    private String fabricante;

    @Column(nullable = false)
    private String nomeReferencia;

    @Column(nullable = false)
    private LocalDateTime vencimentoRegistro;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Medicamento that = (Medicamento) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

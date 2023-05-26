package br.vet.certvet.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "procedimento")
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String descricao;

    private LocalDate dataAplicacao;

    @ManyToOne
    private Prontuario prontuario;

    @OneToMany(mappedBy = "procedimento")
    @ToString.Exclude
    private List<Estoque> medicamentosConsumidos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Procedimento that = (Procedimento) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(descricao, that.descricao))
            return false;
        return Objects.equals(prontuario, that.prontuario);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (prontuario != null ? prontuario.hashCode() : 0);
        return result;
    }
}
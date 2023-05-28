package br.vet.certvet.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "procedimentos")
public class Procedimento {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String descricao;
    private String outros;

    private LocalDate dataAplicacao;

    @ManyToOne
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;

    @OneToOne
    @JoinColumn(name = "estoque_id")
    //TODO: Adicionar tabela de relacionamento
    private Estoque medicamentoConsumido;

    private BigDecimal doseMedicamento;

    public Procedimento(Prontuario prontuario) {
        this.prontuario = prontuario;
    }
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

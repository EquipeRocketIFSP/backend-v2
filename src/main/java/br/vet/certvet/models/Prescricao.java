package br.vet.certvet.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String uso;
    private String nome;
    private String dose;
    private String formaFarmaceutica;
    private String concentracao;
    private String frequencia;
    private String duracao;
    private String quandoAplicar;
    private String observacoes;

    @ManyToOne
    private Procedimento procedimento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prescricao that = (Prescricao) o;

        if (!uso.equals(that.uso)) return false;
        if (!nome.equals(that.nome)) return false;
        if (!dose.equals(that.dose)) return false;
        if (!formaFarmaceutica.equals(that.formaFarmaceutica)) return false;
        if (!concentracao.equals(that.concentracao)) return false;
        if (!frequencia.equals(that.frequencia)) return false;
        if (!duracao.equals(that.duracao)) return false;
        if (!quandoAplicar.equals(that.quandoAplicar)) return false;
        if (!Objects.equals(observacoes, that.observacoes)) return false;
        return procedimento.equals(that.procedimento);
    }

    @Override
    public int hashCode() {
        int result = uso.hashCode();
        result = 31 * result + nome.hashCode();
        result = 31 * result + dose.hashCode();
        result = 31 * result + formaFarmaceutica.hashCode();
        result = 31 * result + concentracao.hashCode();
        result = 31 * result + frequencia.hashCode();
        result = 31 * result + duracao.hashCode();
        result = 31 * result + quandoAplicar.hashCode();
        result = 31 * result + (observacoes != null ? observacoes.hashCode() : 0);
        result = 31 * result + procedimento.hashCode();
        return result;
    }
}

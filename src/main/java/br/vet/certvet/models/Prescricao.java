package br.vet.certvet.models;

import br.vet.certvet.enums.QuandoAplicarPrescricao;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
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
    private int versao;
    private String codigo;

    private String uso;
    private String nome;
    private String dose;
    private String formaFarmaceutica;
    private String concentracao;
    private String frequencia;
    private String duracao;
    private String quandoAplicar;
    private String observacoes;


    //TODO: Corrigir associação
    /*@Setter
    @ManyToOne()
    private List<Usuario> assinadores;*/

    private LocalDate dataCriacao;
    private LocalDate dataExclusao;

    public boolean isDeleted(){
        return dataExclusao != null;
    }

    @ManyToOne
    private Prontuario prontuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prescricao that = (Prescricao) o;

        if (!uso.equals(that.uso)) return false;
        if (!dataCriacao.equals(that.dataCriacao)) return false;
        if (!nome.equals(that.nome)) return false;
        if (!dose.equals(that.dose)) return false;
        if (!formaFarmaceutica.equals(that.formaFarmaceutica)) return false;
        if (!concentracao.equals(that.concentracao)) return false;
        if (!frequencia.equals(that.frequencia)) return false;
        if (!duracao.equals(that.duracao)) return false;
        if (!quandoAplicar.equals(that.quandoAplicar)) return false;
        if (!Objects.equals(observacoes, that.observacoes)) return false;
        return prontuario.equals(that.prontuario);
    }

    @Override
    public int hashCode() {
        int result = uso.hashCode();
        result = 31 * result + dataCriacao.hashCode();
        result = 31 * result + nome.hashCode();
        result = 31 * result + dose.hashCode();
        result = 31 * result + formaFarmaceutica.hashCode();
        result = 31 * result + concentracao.hashCode();
        result = 31 * result + frequencia.hashCode();
        result = 31 * result + duracao.hashCode();
        result = 31 * result + quandoAplicar.hashCode();
        result = 31 * result + (observacoes != null ? observacoes.hashCode() : 0);
        result = 31 * result + prontuario.hashCode();
        return result;
    }

    public void delete() {
        this.dataExclusao = LocalDate.now();
    }

    public Prescricao firstVersion() {
        this.versao = 1;
        return this;
    }

    public Prescricao increaseVersion() {
        this.versao += 1;
        return this;
    }
}

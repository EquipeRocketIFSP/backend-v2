package br.vet.certvet.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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


    @Setter
    @ManyToOne
    @ToString.Exclude
    @JoinTable(
            name = "prescricoes_veterinarioassinador",
            joinColumns = @JoinColumn(name = "prescricao_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "usuarios_id", referencedColumnName = "id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"prescricao_id", "usuarios_id"}) }
    )
    private Usuario veterinarioAssinador;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataExclusao;

    public boolean isDeleted(){
        return dataExclusao != null;
    }

    @ManyToOne
    @ToString.Exclude
    @JoinTable(
            name = "prontuario_prescricoes",
            joinColumns = @JoinColumn(name = "prontuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "prescricoes_id", referencedColumnName = "id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"prontuario_id", "prescricoes_id"}) }
    )
    private Prontuario prontuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prescricao that = (Prescricao) o;

        if (versao != that.versao) return false;
        if (!Objects.equals(codigo, that.codigo)) return false;
        if (!Objects.equals(uso, that.uso)) return false;
        if (!Objects.equals(nome, that.nome)) return false;
        if (!Objects.equals(dose, that.dose)) return false;
        if (!Objects.equals(formaFarmaceutica, that.formaFarmaceutica))
            return false;
        if (!Objects.equals(concentracao, that.concentracao)) return false;
        if (!Objects.equals(frequencia, that.frequencia)) return false;
        if (!Objects.equals(duracao, that.duracao)) return false;
        if (!Objects.equals(quandoAplicar, that.quandoAplicar))
            return false;
        return Objects.equals(observacoes, that.observacoes);
    }

    @Override
    public int hashCode() {
        int result = versao;
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 31 * result + (uso != null ? uso.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (dose != null ? dose.hashCode() : 0);
        result = 31 * result + (formaFarmaceutica != null ? formaFarmaceutica.hashCode() : 0);
        result = 31 * result + (concentracao != null ? concentracao.hashCode() : 0);
        result = 31 * result + (frequencia != null ? frequencia.hashCode() : 0);
        result = 31 * result + (duracao != null ? duracao.hashCode() : 0);
        result = 31 * result + (quandoAplicar != null ? quandoAplicar.hashCode() : 0);
        result = 31 * result + (observacoes != null ? observacoes.hashCode() : 0);
        result = 31 * result + (dataCriacao != null ? dataCriacao.hashCode() : 0);
        result = 31 * result + (dataExclusao != null ? dataExclusao.hashCode() : 0);
        return result;
    }

    public Prescricao delete() {
        this.dataExclusao = LocalDateTime.now();
        return this;
    }

    public Prescricao increaseVersion() {
        this.versao += 1;
        this.dataCriacao = LocalDateTime.now();
        return this;
    }

    public Prescricao setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
        return this;
    }

    public Prescricao setDataCriacao() {
        this.versao = 1;
        this.dataCriacao = LocalDateTime.now();
        return this;
    }

    public Prescricao setVersao(int versao){
        this.versao = versao;
        return this;
    }
}

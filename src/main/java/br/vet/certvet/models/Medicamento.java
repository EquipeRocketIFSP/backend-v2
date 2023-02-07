package br.vet.certvet.models;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.models.contracts.Fillable;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento implements Fillable<MedicamentoRequestDto> {

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
    private String dose;

    @Column(nullable = false)
    private String concentracao;

    @Column(nullable = false)
    private String intervaloDose;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    public Medicamento(MedicamentoRequestDto dto, Clinica clinica) {
        this.clinica = clinica;
        this.fill(dto);
    }

    @Override
    public void fill(MedicamentoRequestDto dto) {
        this.nome = dto.nome;
        this.codigoRegistro = dto.codigoRegistro;
        this.principioAtivo = dto.principioAtivo;
        this.concentracao = dto.concentracao;
        this.dose = dto.dose;
        this.viaUso = dto.viaUso;
        this.intervaloDose = dto.intervaloDose;
    }

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

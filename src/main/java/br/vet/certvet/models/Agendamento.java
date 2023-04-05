package br.vet.certvet.models;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
import br.vet.certvet.models.contracts.Fillable;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class Agendamento implements Fillable<AgendamentoRequestDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    @Setter
    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Usuario veterinario;

    @Setter
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Setter
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Usuario tutor;

    @Setter
    @Column(nullable = false, length = 1000)
    private String observacoes;

    @Setter
    @Column(nullable = false)
    private LocalDateTime dataConsulta;

    @CreationTimestamp
    private LocalDateTime criadoEm;

    public Agendamento(AgendamentoRequestDto dto, Usuario veterinario, Animal animal, Usuario tutor, Clinica clinica) {
        this.fill(dto);

        this.veterinario = veterinario;
        this.animal = animal;
        this.tutor = tutor;
        this.clinica = clinica;
    }

    @Override
    public void fill(AgendamentoRequestDto dto) {
        this.observacoes = dto.observacoes();
        this.dataConsulta = dto.dataConsulta();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Agendamento that = (Agendamento) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

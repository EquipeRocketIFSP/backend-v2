package br.vet.certvet.models;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
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
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Usuario tutor;

    @Column(nullable = false, length = 1000)
    private String observacoes;

    @Column(nullable = false)
    private LocalDateTime dataConsulta;

    @CreationTimestamp
    private LocalDateTime criadoEm;

    public Agendamento(AgendamentoRequestDto dto, Animal animal, Usuario tutor, Clinica clinica) {
        this.observacoes = dto.observacoes;
        this.dataConsulta = dto.dataConsulta;
        this.animal = animal;
        this.tutor = tutor;
        this.clinica = clinica;
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

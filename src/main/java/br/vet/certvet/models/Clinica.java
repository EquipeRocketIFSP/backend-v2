package br.vet.certvet.models;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.ClinicaRequestDto;
import br.vet.certvet.models.contracts.Fillable;
import lombok.*;
import net.bytebuddy.utility.RandomString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Table(name = "clinicas")
public class Clinica implements Fillable<ClinicaRequestDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String nomeFantasia;

    @Setter
    @Column(nullable = false)
    private String razaoSocial;

    @Setter
    @Column(nullable = false, unique = true)
    @CNPJ
    private String cnpj;

    @Setter
    @Column(nullable = false)
    private String cnae;

    @Setter
    @Column(nullable = false, length = 9)
    private String cep;

    @Setter
    @Column(nullable = false)
    private String logradouro;

    @Setter
    @Column(nullable = false, length = 6)
    private String numero;

    @Setter
    @Column(nullable = false)
    private String bairro;

    @Setter
    @Column(nullable = false)
    private String cidade;

    @Setter
    @Column(nullable = false, length = 2)
    private String estado;

    @Setter
    @Column(nullable = false, length = 15)
    private String celular;

    @Setter
    @Column(length = 14)
    private String telefone;

    @Setter
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

    @OneToMany
    @JoinTable(
            name = "clinica_usuarios",
            joinColumns = @JoinColumn(name = "clinica_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private List<Usuario> usuarios;

    @Setter
    @OneToOne
    private Usuario responsavelTecnico;

    @OneToMany
    @ToString.Exclude
    private List<Agendamento> agendamentos;

    /*@OneToMany
    @ToString.Exclude
    private List<Estoque> estoque;*/

    @OneToMany
    @ToString.Exclude
    private List<Prontuario> prontuarios;

    public void setProntuarios(List<Prontuario> prontuarios) {
        this.prontuarios = prontuarios;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Clinica(ClinicaInicialRequestDto dto) {
        this.nomeFantasia = dto.clinicaNomeFantasia();
        this.razaoSocial = dto.clinicaRazaoSocial();
        this.cep = dto.clinicaCep();
        this.logradouro = dto.clinicaLogradouro();
        this.numero = dto.clinicaNumero();
        this.bairro = dto.clinicaBairro();
        this.cidade = dto.clinicaCidade();
        this.estado = dto.clinicaEstado();
        this.cnae = dto.clinicaCnae();
        this.cnpj = dto.clinicaCnpj();
        this.email = dto.clinicaEmail();
        this.celular = dto.clinicaCelular();
        this.telefone = dto.clinicaTelefone();

        this.code = RandomString.hashOf(this.cnpj);
    }

    @Override
    public void fill(ClinicaRequestDto dto) {
        this.nomeFantasia = dto.nomeFantasia();
        this.razaoSocial = dto.razaoSocial();
        this.cep = dto.cep();
        this.logradouro = dto.logradouro();
        this.numero = dto.numero();
        this.bairro = dto.bairro();
        this.cidade = dto.cidade();
        this.estado = dto.estado();
        this.cnae = dto.cnae();
        this.cnpj = dto.cnpj();
        this.email = dto.email();
        this.celular = dto.celular();
        this.telefone = dto.telefone();
    }

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

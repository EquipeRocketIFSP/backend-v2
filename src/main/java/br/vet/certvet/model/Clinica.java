package br.vet.certvet.model;

import br.vet.certvet.dto.request.ClinicaInicialRequestDto;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class Clinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    public String nomeFantasia;

    @Column(nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    public String cnpj;

    @Column(nullable = false)
    public String cnae;

    @Column(nullable = false)
    public String cep;

    @Column(nullable = false)
    public String logradouro;

    @Column(nullable = false)
    public String numero;

    @Column(nullable = false)
    public String bairro;

    @Column(nullable = false)
    public String cidade;

    @Column(nullable = false)
    public String estado;

    @Column(nullable = false)
    public String celular;

    @Column(nullable = false)
    public String telefone;

    @Column(nullable = false)
    public String email;

    @OneToMany
    @JoinTable(
            name = "clinica_usuarios",
            joinColumns = @JoinColumn(name = "clinica_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private List<Usuario> usuarios;

    @OneToMany
    @ToString.Exclude
    private List<Agendamento> agendamentos;

    /*@OneToMany
    @ToString.Exclude
    private List<Estoque> estoque;*/

    @OneToMany
    @ToString.Exclude
    private List<Prontuario> prontuarios;

    public Clinica(ClinicaInicialRequestDto dto) {
        this.nomeFantasia = dto.clinica_nome_fantasia;
        this.razaoSocial = dto.clinica_razao_social;
        this.cep = dto.clinica_cep;
        this.logradouro = dto.clinica_logradouro;
        this.numero = dto.clinica_numero;
        this.bairro = dto.clinica_bairro;
        this.cidade = dto.clinica_cidade;
        this.estado = dto.clinica_estado;
        this.cnae = dto.clinica_cnae;
        this.cnpj = dto.clinica_cnpj;
        this.email = dto.clinica_email;
        this.celular = dto.clinica_celular;
        this.telefone = dto.clinica_telefone;
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

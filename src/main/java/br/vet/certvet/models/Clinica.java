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
    public String nomeFantasia;

    @Setter
    @Column(nullable = false)
    private String razaoSocial;

    @Setter
    @Column(nullable = false, unique = true)
    @CNPJ
    public String cnpj;

    @Setter
    @Column(nullable = false)
    public String cnae;

    @Setter
    @Column(nullable = false, length = 9)
    public String cep;

    @Setter
    @Column(nullable = false)
    public String logradouro;

    @Setter
    @Column(nullable = false, length = 6)
    public String numero;

    @Setter
    @Column(nullable = false)
    public String bairro;

    @Setter
    @Column(nullable = false)
    public String cidade;

    @Setter
    @Column(nullable = false, length = 2)
    public String estado;

    @Setter
    @Column(nullable = false, length = 15)
    public String celular;

    @Setter
    @Column(length = 14)
    public String telefone;

    @Setter
    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String code;

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

        this.code = RandomString.hashOf(this.cnpj);
    }

    @Override
    public void fill(ClinicaRequestDto dto) {
        this.nomeFantasia = dto.nome_fantasia;
        this.razaoSocial = dto.razao_social;
        this.cep = dto.cep;
        this.logradouro = dto.logradouro;
        this.numero = dto.numero;
        this.bairro = dto.bairro;
        this.cidade = dto.cidade;
        this.estado = dto.estado;
        this.cnae = dto.cnae;
        this.cnpj = dto.cnpj;
        this.email = dto.email;
        this.celular = dto.celular;
        this.telefone = dto.telefone;
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

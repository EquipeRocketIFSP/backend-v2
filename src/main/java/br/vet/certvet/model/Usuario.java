package br.vet.certvet.model;

import br.vet.certvet.dto.request.UsuarioAtivoRequestDto;
import br.vet.certvet.dto.request.UsuarioRequestDto;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Email
    @Setter
    @Column(nullable = false)
    private String username;
    @Setter
    private String password;

    @Setter
    @Column(nullable = false)
    public String nome;

    @Setter
    @Column(nullable = false, length = 14)
    public String cpf;

    @Setter
    @Column(nullable = false)
    public String rg;

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
    @Column(nullable = false, length = 14)
    public String celular;

    @Setter
    @Column(length = 13)
    public String telefone;

    @Setter
    private String crmv;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    private Clinica clinica;

    @ManyToMany
    @JoinTable(name = "Usuario_authorities",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Authority> authorities;

    public Usuario(UsuarioRequestDto dto, Clinica clinica) {
        this.username = dto.email;
        this.nome = dto.nome;
        this.cpf = dto.cpf;
        this.rg = dto.rg;
        this.cep = dto.cep;
        this.logradouro = dto.logradouro;
        this.numero = dto.numero;
        this.bairro = dto.bairro;
        this.cidade = dto.cidade;
        this.estado = dto.estado;
        this.celular = dto.celular;
        this.telefone = dto.telefone;
        this.clinica = clinica;
        this.crmv = null;
        this.password = null;

        if (dto instanceof UsuarioAtivoRequestDto) {
            this.crmv = ((UsuarioAtivoRequestDto) dto).crmv;
            this.password = ((UsuarioAtivoRequestDto) dto).senha;
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Usuario usuario = (Usuario) o;
        return id != null && Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

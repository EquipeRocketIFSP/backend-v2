package br.vet.certvet.models;

import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.models.contracts.Fillable;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario implements UserDetails, Fillable<UsuarioRequestDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Email
    @Setter
    @Column(nullable = false)
    private String username;
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
    @Column(nullable = false, length = 15)
    public String celular;

    @Setter
    @Column(length = 15)
    public String telefone;

    @Setter
    private String crmv;

    @Setter
    private LocalDateTime deletedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    private Clinica clinica;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Usuario_authorities",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Authority> authorities;

    public Usuario(UsuarioRequestDto dto, Clinica clinica) {
        this.clinica = clinica;

        this.fill(dto);
    }

    @Override
    public void fill(UsuarioRequestDto dto) {
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
        this.crmv = null;
        this.deletedAt = null;
        this.password = null;
        this.authorities = new ArrayList<>();

        if (dto instanceof FuncionarioRequestDto)
            this.setPassword(((FuncionarioRequestDto) dto).senha);

        if (dto instanceof VeterinarioRequestDto)
            this.crmv = ((VeterinarioRequestDto) dto).crmv;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.deletedAt == null;
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

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}

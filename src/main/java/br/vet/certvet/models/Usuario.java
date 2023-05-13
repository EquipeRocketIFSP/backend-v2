package br.vet.certvet.models;

import br.vet.certvet.dto.requests.*;
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
//    @Column(name = "id")
    private Long id;

    @Email
    @Setter
    @Column(nullable = false)
    private String username;

    private String password;

    @Setter
    @Column(nullable = false)
    private String nome;

    @Setter
    @Column(nullable = false, length = 14)
    private String cpf;

    @Setter
    @Column(nullable = false)
    private String rg;

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
    @Column(length = 15)
    private String telefone;

    @Setter
    private String crmv;

    @Setter
    private LocalDateTime deletedAt;

    @Setter
    private String resetPasswordToken;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    @ToString.Exclude
    private Clinica clinica;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Usuario_authorities",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
    private List<Authority> authorities;
    private String email;

    @ManyToMany
    @ToString.Exclude
    private List<Documento> assinaturas;

    public void setAssinaturas(List<Documento> assinaturas) {
        this.assinaturas = assinaturas;
    }

    public Usuario(UsuarioRequestDto dto, Clinica clinica) {
        this.clinica = clinica;
        this.authorities = new ArrayList<>();
        this.deletedAt = null;

        this.fill(dto);
    }

    @Override
    public void fill(UsuarioRequestDto dto) {
        this.username = dto.getEmail();
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.rg = dto.getRg();
        this.cep = dto.getCep();
        this.logradouro = dto.getLogradouro();
        this.numero = dto.getNumero();
        this.bairro = dto.getBairro();
        this.cidade = dto.getCidade();
        this.estado = dto.getEstado();
        this.celular = dto.getCelular();
        this.telefone = dto.getTelefone();
        this.crmv = null;

        if (!(dto instanceof FuncionarioRequestDto))
            this.password = null;

        if (dto instanceof FuncionarioRequestDto && ((FuncionarioRequestDto) dto).getSenha() != null && !((FuncionarioRequestDto) dto).getSenha().isEmpty())
            this.setPassword(((FuncionarioRequestDto) dto).getSenha());

        if (dto instanceof VeterinarioRequestDto)
            this.crmv = ((VeterinarioRequestDto) dto).getCrmv();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.deletedAt == null;
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

    public String getRegistroCRMV() {
        return this.crmv;
    }

    public String getEmail() {
        return this.email;
    }

    public String getEnderecoCompleto(){
        return String.format("%s, %s - CEP: %s - %s - %s/%s", logradouro, numero, cep, bairro, cidade, estado);
    }
}

package br.vet.certvet.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Table(name = "responsabilidades")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column()
    private Long id;

    @Column(unique = true)
    private String permissao;

    @ManyToMany(mappedBy = "authorities")
    @ToString.Exclude
    private List<Usuario> users;

    public void setUsers(List<Usuario> users) {
        this.users = users;
    }

    public Authority(String authority) {
        this.permissao = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        if (!id.equals(authority.id)) return false;
        if (!Objects.equals(permissao, authority.permissao)) return false;
        return Objects.equals(users, authority.users);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String getAuthority() {
        return permissao;
    }
}

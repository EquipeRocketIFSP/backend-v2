package br.vet.certvet.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parentesco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String grauParentesco;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Parentesco)) return false;
        final Parentesco other = (Parentesco) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$grauParentesco = this.getGrauParentesco();
        final Object other$grauParentesco = other.getGrauParentesco();
        if (this$grauParentesco == null ? other$grauParentesco != null : !this$grauParentesco.equals(other$grauParentesco))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Parentesco;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $grauParentesco = this.getGrauParentesco();
        result = result * PRIME + ($grauParentesco == null ? 43 : $grauParentesco.hashCode());
        return result;
    }
}

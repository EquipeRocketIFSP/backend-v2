package br.vet.certvet.model;

import javax.persistence.*;

@Entity
@Table(name = "clinica", schema = "certvet", catalog = "")
public class ClinicaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "razao_social")
    private String razaoSocial;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClinicaEntity that = (ClinicaEntity) o;

        if (id != that.id) return false;
        if (razaoSocial != null ? !razaoSocial.equals(that.razaoSocial) : that.razaoSocial != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (razaoSocial != null ? razaoSocial.hashCode() : 0);
        return result;
    }
}

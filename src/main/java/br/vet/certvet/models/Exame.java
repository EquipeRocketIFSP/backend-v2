package br.vet.certvet.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Accessors(chain = true)
@Table(name = "exame")
@ToString
public class Exame {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String tipoExame;// TODO: Alterar para enum

    @Column(length = 2000)
    private String outrosExames;

    @Column(length = 2000)
    private String outrosCitologia;

    private String bioquimico; // TODO: Alterar para enum
    private String hematologia; // TODO: Alterar para enum
    private String citologia; // TODO: Alterar para enum
    private String imagem; // TODO: Alterar para enum
    private String imagemRegiaoCervical;
    private String imagemRegiaoAbdomen;
    private String imagemRegiaoMToracicos;
    private String imagemRegiaoMPelvicos;
    private String imagemObsRegioes;
    private boolean imagemRegiaoCabeca;
    private boolean imagemRegiaoTorax;

    @ManyToOne
    @JoinTable(
            name = "prontuario_exames",
            joinColumns = @JoinColumn(name = "exames_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "prontuario_id", referencedColumnName = "id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"prontuario_id", "exames_id"}) }
    )
//    @JsonBackReference
    private Prontuario prontuario;


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exame exame = (Exame) o;

        if (imagemRegiaoCabeca != exame.imagemRegiaoCabeca) return false;
        if (imagemRegiaoTorax != exame.imagemRegiaoTorax) return false;
        if (!Objects.equals(tipoExame, exame.tipoExame))
            return false;
        if (!Objects.equals(outrosExames, exame.outrosExames))
            return false;
        if (!Objects.equals(outrosCitologia, exame.outrosCitologia))
            return false;
        if (!Objects.equals(bioquimico, exame.bioquimico))
            return false;
        if (!Objects.equals(hematologia, exame.hematologia))
            return false;
        if (!Objects.equals(citologia, exame.citologia))
            return false;
        if (!Objects.equals(imagem, exame.imagem))
            return false;
        if (!Objects.equals(imagemRegiaoCervical, exame.imagemRegiaoCervical))
            return false;
        if (!Objects.equals(imagemRegiaoAbdomen, exame.imagemRegiaoAbdomen))
            return false;
        if (!Objects.equals(imagemRegiaoMToracicos, exame.imagemRegiaoMToracicos))
            return false;
        return Objects.equals(imagemRegiaoMPelvicos, exame.imagemRegiaoMPelvicos);
    }

    @Override
    public int hashCode() {
        int result = tipoExame != null ? tipoExame.hashCode() : 0;
        result = 31 * result + (outrosExames != null ? outrosExames.hashCode() : 0);
        result = 31 * result + (outrosCitologia != null ? outrosCitologia.hashCode() : 0);
        result = 31 * result + (bioquimico != null ? bioquimico.hashCode() : 0);
        result = 31 * result + (hematologia != null ? hematologia.hashCode() : 0);
        result = 31 * result + (citologia != null ? citologia.hashCode() : 0);
        result = 31 * result + (imagem != null ? imagem.hashCode() : 0);
        result = 31 * result + (imagemRegiaoCervical != null ? imagemRegiaoCervical.hashCode() : 0);
        result = 31 * result + (imagemRegiaoAbdomen != null ? imagemRegiaoAbdomen.hashCode() : 0);
        result = 31 * result + (imagemRegiaoMToracicos != null ? imagemRegiaoMToracicos.hashCode() : 0);
        result = 31 * result + (imagemRegiaoMPelvicos != null ? imagemRegiaoMPelvicos.hashCode() : 0);
        result = 31 * result + (imagemRegiaoCabeca ? 1 : 0);
        result = 31 * result + (imagemRegiaoTorax ? 1 : 0);
        return result;
    }
    public Exame(Prontuario prontuario) {
        this.prontuario = prontuario;
    }
}
package br.vet.certvet.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Accessors(chain = true)
@Table(name = "exame")
public class Exame {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String tipoExame;

    @Column(length = 2000)
    private String outrosExames;

    @Column(length = 2000)
    private String outrosCitologia;

    private String bioquimico;
    private String hematologia;
    private String citologia;
    private String imagem;
    private String imagemRegiaoCervical;
    private String imagemRegiaoAbdomen;
    private String imagemRegiaoMToracicos;
    private String imagemRegiaoMPelvicos;

    private boolean imagemRegiaoCabeca;
    private boolean imagemRegiaoTorax;

    @ManyToOne
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;

    public Exame(Prontuario prontuario) {
        this.prontuario = prontuario;
    }
}
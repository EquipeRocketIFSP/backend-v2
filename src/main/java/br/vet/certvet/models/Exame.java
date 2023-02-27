package br.vet.certvet.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "exame")
public class Exame {
    @Id
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
}
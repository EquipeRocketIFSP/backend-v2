package br.vet.certvet.models;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prontuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Clinica clinica;

    @Column(nullable = false)
    private int frequenciaCardiaca;

    @Column(nullable = false)
    private int frequenciaRespiratoria;

    @Column(nullable = false)
    private int temperatura;

    @Column(nullable = false)
    private String hidratacao;

    @Column(nullable = false)
    private String tpc;

    @Column(nullable = false)
    private String mucosa;

    @Column(nullable = false)
    private String conciencia;

    @Column(nullable = false)
    private String escoreCorporal;

    @Column(length = 2000)
    private String supeitaDiagnostica;

    @Column(nullable = false)
    private boolean prostracao;

    @Column(nullable = false)
    private boolean febre;

    @Column(nullable = false)
    private boolean vomito;

    @Column(nullable = false)
    private boolean diarreia;

    @Column(nullable = false)
    private boolean espasmosConvulsao;

    @Column(nullable = false)
    private boolean deambulacao;

    @Column(nullable = false)
    private boolean sensibilidadeDor;

    @Column(nullable = false)
    private boolean lesoesNodulos;

    private String apetite;
    private String linfonodos;
    private String linfonodosObs;
    private String regiaoCervical;
    private String regiaoAbdomen;
    private String regiaoMToracicos;
    private String regiaoMPelvicos;

    private boolean regiaoCabeca;
    private boolean regiaoTorax;

    private LocalDateTime dataAtendimento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario veterinario;

    @OneToOne
    private Cirurgia cirurgia;

    @OneToMany(mappedBy = "prontuario")
    @ToString.Exclude
    private List<Procedimento> procedimentos;

    @OneToMany(mappedBy = "prontuario")
    @ToString.Exclude
    private List<Exame> exames;
    private String codigo;

    @ManyToOne
    private Usuario tutor;

    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    private List<Documento> documentos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Prontuario that = (Prontuario) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String getCodigo() {
        return this.codigo;
    }

}

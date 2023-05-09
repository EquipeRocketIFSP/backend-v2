package br.vet.certvet.models;


import br.vet.certvet.enums.ProntuarioStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Accessors(chain = true)
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

    @Setter
    private int frequenciaCardiaca;

    @Setter
    private int frequenciaRespiratoria;

    @Setter
    private int temperatura;

    @Setter
    private String peso;

    @Setter
    private String hidratacao;

    @Setter
    private String tpc;

    @Setter
    private String mucosa;

    @Setter
    private String conciencia;

    @Setter
    private String escoreCorporal;

    @Setter
    @Column(length = 2000)
    private String supeitaDiagnostica;
    private boolean prostracao;
    private boolean febre;
    private boolean vomito;
    private boolean diarreia;
    private boolean espasmosConvulsao;
    private boolean deambulacao;
    private boolean sensibilidadeDor;
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

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Setter
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

    @Setter
    @ManyToOne
    @Accessors(chain = true)
    private Usuario tutor;

    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    private List<Documento> documentos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProntuarioStatus status = ProntuarioStatus.PENDING;

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

    public Prontuario setCodigo(LocalDateTime now) {
        this.codigo = "VT-P-" + now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        return this;
    }
}

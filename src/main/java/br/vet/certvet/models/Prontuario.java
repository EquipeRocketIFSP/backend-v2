package br.vet.certvet.models;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private Integer versao;

    @Setter
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

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Usuario tutor;

    @OneToMany(mappedBy = "id") // removido atributo mappedBy por não se tratar de uma relação bidirecional
    @ToString.Exclude
    private List<Documento> documentos;
    private Date criadoEm;

    public static String createCodigo(LocalDateTime now) {
        // exemplo: VT-P-2022_12_03_02_19_20.pdf
        return "VT-P-"+now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
    }

    public void setTutor(Usuario tutor) {
        this.tutor = tutor;
    }

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

    final public String getMonthAtendimento(){
        final var month = new DateFormatSymbols().getMonths()[
                dataAtendimento.getMonth()
                        .getValue()-1
                ]
                .toLowerCase();
        return month.substring(0, 1)
                .toUpperCase() +
                month.substring(1);
    }

    public Prontuario addDocumentoPdf(Documento documento) {
        documentos = Arrays.asList(documento);
        return this;
    }
    public Prontuario setDocumentoDetails(Documento documento){
        return addDocumentoPdf(documento)
                .setCodigo(documento.getCodigo())
                .setVersao(documento.getVersao())
                .setCriadoEm(documento.getCriadoEm());
    }
    
    private Prontuario setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
        return this;
    }
    public Prontuario setVersao(int versao) {
        this.versao = versao;
        return this;
    }

    public Prontuario setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }
    public Prontuario setClinica(Clinica clinica) {
        this.clinica = clinica;
        return this;
    }

    public Prontuario setVeterinario(Usuario veterinario) {
        this.veterinario = veterinario;
        return this;
    }

    public Prontuario setAnimal(Animal animal) {
        this.animal = animal;
        return this;
    }
}

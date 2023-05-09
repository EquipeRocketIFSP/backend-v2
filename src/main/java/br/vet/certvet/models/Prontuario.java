package br.vet.certvet.models;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
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

    private Integer versao;

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
    @JoinColumn(name = "tutor_id")
    private Usuario tutor;

    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    private List<Documento> documentos;
    private Date criadoEm;

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    public void setProcedimentos(List<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
    }

    public static String createCodigo(LocalDateTime now) {
        // exemplo: VT-P-2022_12_03_02_19_20.pdf
        return "VT-P-"+now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
    }

    public void setTutor(Usuario tutor) {
        this.tutor = tutor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prontuario that = (Prontuario) o;

        if (frequenciaCardiaca != that.frequenciaCardiaca) return false;
        if (frequenciaRespiratoria != that.frequenciaRespiratoria) return false;
        if (temperatura != that.temperatura) return false;
        if (prostracao != that.prostracao) return false;
        if (febre != that.febre) return false;
        if (vomito != that.vomito) return false;
        if (diarreia != that.diarreia) return false;
        if (espasmosConvulsao != that.espasmosConvulsao) return false;
        if (deambulacao != that.deambulacao) return false;
        if (sensibilidadeDor != that.sensibilidadeDor) return false;
        if (lesoesNodulos != that.lesoesNodulos) return false;
        if (regiaoCabeca != that.regiaoCabeca) return false;
        if (regiaoTorax != that.regiaoTorax) return false;
        if (!id.equals(that.id)) return false;
        if (!Objects.equals(versao, that.versao)) return false;
        if (!Objects.equals(hidratacao, that.hidratacao)) return false;
        if (!Objects.equals(tpc, that.tpc)) return false;
        if (!Objects.equals(mucosa, that.mucosa)) return false;
        if (!Objects.equals(conciencia, that.conciencia)) return false;
        if (!Objects.equals(escoreCorporal, that.escoreCorporal))
            return false;
        if (!Objects.equals(supeitaDiagnostica, that.supeitaDiagnostica))
            return false;
        if (!Objects.equals(apetite, that.apetite)) return false;
        if (!Objects.equals(linfonodos, that.linfonodos)) return false;
        if (!Objects.equals(linfonodosObs, that.linfonodosObs))
            return false;
        if (!Objects.equals(regiaoCervical, that.regiaoCervical))
            return false;
        if (!Objects.equals(regiaoAbdomen, that.regiaoAbdomen))
            return false;
        if (!Objects.equals(regiaoMToracicos, that.regiaoMToracicos))
            return false;
        if (!Objects.equals(regiaoMPelvicos, that.regiaoMPelvicos))
            return false;
        if (!Objects.equals(dataAtendimento, that.dataAtendimento))
            return false;
        if (!Objects.equals(animal, that.animal)) return false;
        if (!Objects.equals(veterinario, that.veterinario)) return false;
        if (!Objects.equals(cirurgia, that.cirurgia)) return false;
        if (!Objects.equals(codigo, that.codigo)) return false;
        return Objects.equals(criadoEm, that.criadoEm);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (versao != null ? versao.hashCode() : 0);
        result = 31 * result + frequenciaCardiaca;
        result = 31 * result + frequenciaRespiratoria;
        result = 31 * result + temperatura;
        result = 31 * result + (hidratacao != null ? hidratacao.hashCode() : 0);
        result = 31 * result + (tpc != null ? tpc.hashCode() : 0);
        result = 31 * result + (mucosa != null ? mucosa.hashCode() : 0);
        result = 31 * result + (conciencia != null ? conciencia.hashCode() : 0);
        result = 31 * result + (escoreCorporal != null ? escoreCorporal.hashCode() : 0);
        result = 31 * result + (supeitaDiagnostica != null ? supeitaDiagnostica.hashCode() : 0);
        result = 31 * result + (prostracao ? 1 : 0);
        result = 31 * result + (febre ? 1 : 0);
        result = 31 * result + (vomito ? 1 : 0);
        result = 31 * result + (diarreia ? 1 : 0);
        result = 31 * result + (espasmosConvulsao ? 1 : 0);
        result = 31 * result + (deambulacao ? 1 : 0);
        result = 31 * result + (sensibilidadeDor ? 1 : 0);
        result = 31 * result + (lesoesNodulos ? 1 : 0);
        result = 31 * result + (apetite != null ? apetite.hashCode() : 0);
        result = 31 * result + (linfonodos != null ? linfonodos.hashCode() : 0);
        result = 31 * result + (linfonodosObs != null ? linfonodosObs.hashCode() : 0);
        result = 31 * result + (regiaoCervical != null ? regiaoCervical.hashCode() : 0);
        result = 31 * result + (regiaoAbdomen != null ? regiaoAbdomen.hashCode() : 0);
        result = 31 * result + (regiaoMToracicos != null ? regiaoMToracicos.hashCode() : 0);
        result = 31 * result + (regiaoMPelvicos != null ? regiaoMPelvicos.hashCode() : 0);
        result = 31 * result + (regiaoCabeca ? 1 : 0);
        result = 31 * result + (regiaoTorax ? 1 : 0);
        result = 31 * result + (dataAtendimento != null ? dataAtendimento.hashCode() : 0);
        result = 31 * result + (animal != null ? animal.hashCode() : 0);
        result = 31 * result + (veterinario != null ? veterinario.hashCode() : 0);
        result = 31 * result + (cirurgia != null ? cirurgia.hashCode() : 0);
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 31 * result + (criadoEm != null ? criadoEm.hashCode() : 0);
        return result;
    }
}

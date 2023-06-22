package br.vet.certvet.models;


import br.vet.certvet.enums.ProntuarioStatus;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Getter
@Accessors(chain = true)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "prontuarios")
public class Prontuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter(AccessLevel.NONE)
    private Integer versao = 0;

    @ManyToOne
    @Setter(AccessLevel.NONE)
    private Clinica clinica;

    private int frequenciaCardiaca;
    private int frequenciaRespiratoria;
    private byte temperatura;
    private String peso;
    private String hidratacao;
    private String tpc;
    private String mucosa;
    private String conciencia;
    private String escoreCorporal;
    private Date criadoEm;

    @Column(length = 2000)
    private String supeitaDiagnostica;

    private LocalDateTime dataAtendimento;

    @Setter(AccessLevel.NONE)
    private String codigo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_id", nullable = false)
    @ToString.Exclude
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    @ToString.Exclude
    private Usuario veterinario;

    @OneToOne(mappedBy = "prontuario")
    @ToString.Exclude
    private Cirurgia cirurgia;

    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Procedimento> procedimentos = new ArrayList<>();

    @OneToMany(mappedBy = "prontuario")
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<Exame> exames = new ArrayList<>();

    @ManyToOne
    @Accessors(chain = true)
    @JoinColumn(name = "tutor_id")
    @ToString.Exclude
    private Usuario tutor;

    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<Documento> documentos = new ArrayList<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "prontuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private ManifestacoesClinicas manifestacoesClinicas;

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinTable(
            name = "prontuario_linfonodos",
            joinColumns = @JoinColumn(name = "prontuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "linfonodo_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"prontuario_id", "linfonodo_id"})}
    )
    private List<Linfonodo> linfonodos = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinTable(
            name = "prontuario_musculos",
            joinColumns = @JoinColumn(name = "prontuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "musculos_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"prontuario_id", "musculos_id"})}
    )
    private List<Musculo> musculos = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinTable(
            name = "prontuario_coluna_regioes",
            joinColumns = @JoinColumn(name = "prontuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coluna_regioes_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"prontuario_id", "coluna_regioes_id"})}
    )
    private List<ColunaRegioes> colunaRegioes = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinTable(
            name = "prontuario_abdomen_regioes",
            joinColumns = @JoinColumn(name = "prontuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "abdomen_regioes_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"prontuario_id", "abdomen_regioes_id"})}
    )
    private List<AbdomenRegioes> abdomenRegioes = new ArrayList<>();

    @OneToMany(mappedBy = "prontuario")
    @ToString.Exclude
    private List<Prescricao> prescricoes;

    public Prontuario setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
        return this;
    }

    public Prontuario setProcedimentos(List<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
        return this;
    }

    public static String createCodigo(LocalDateTime now) {
        // exemplo: VT-P-2022_12_03_02_19_20.pdf
        return "VT-P-" + now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
    }

    public List<Prescricao> getPrescricoes(int versao) {
        return prescricoes.stream()
                .filter(prescricao -> !prescricao.isDeleted())
                .filter(prescricao -> prescricao.getVersao() == versao)
                .toList();
    }

    public final String getMonthAtendimento() {
        final var month = new DateFormatSymbols().getMonths()[
                dataAtendimento.getMonth()
                        .getValue() - 1
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

    public Prontuario setDocumentoDetails(Documento documento) {
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProntuarioStatus status = ProntuarioStatus.PENDING;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prontuario that = (Prontuario) o;

        if (frequenciaCardiaca != that.frequenciaCardiaca) return false;
        if (frequenciaRespiratoria != that.frequenciaRespiratoria) return false;
        if (temperatura != that.temperatura) return false;
        if (!Objects.equals(versao, that.versao))
            return false;
        if (!Objects.equals(clinica, that.clinica))
            return false;
        if (!Objects.equals(peso, that.peso))
            return false;
        if (!Objects.equals(hidratacao, that.hidratacao))
            return false;
        if (!Objects.equals(tpc, that.tpc))
            return false;
        if (!Objects.equals(mucosa, that.mucosa))
            return false;
        if (!Objects.equals(conciencia, that.conciencia))
            return false;
        if (!Objects.equals(escoreCorporal, that.escoreCorporal))
            return false;
        if (!Objects.equals(supeitaDiagnostica, that.supeitaDiagnostica))
            return false;
        if (!Objects.equals(linfonodos, that.linfonodos))
            return false;
        if (!Objects.equals(dataAtendimento, that.dataAtendimento))
            return false;
        if (!Objects.equals(animal, that.animal))
            return false;
        if (!Objects.equals(veterinario, that.veterinario))
            return false;
        if (!Objects.equals(cirurgia, that.cirurgia))
            return false;
        if (!Objects.equals(codigo, that.codigo))
            return false;
        if (!Objects.equals(criadoEm, that.criadoEm))
            return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = versao != null ? versao.hashCode() : 0;
        result = 31 * result + (clinica != null ? clinica.hashCode() : 0);
        result = 31 * result + frequenciaCardiaca;
        result = 31 * result + frequenciaRespiratoria;
        result = 31 * result + temperatura;
        result = 31 * result + (peso != null ? peso.hashCode() : 0);
        result = 31 * result + (hidratacao != null ? hidratacao.hashCode() : 0);
        result = 31 * result + (tpc != null ? tpc.hashCode() : 0);
        result = 31 * result + (mucosa != null ? mucosa.hashCode() : 0);
        result = 31 * result + (conciencia != null ? conciencia.hashCode() : 0);
        result = 31 * result + (escoreCorporal != null ? escoreCorporal.hashCode() : 0);
        result = 31 * result + (supeitaDiagnostica != null ? supeitaDiagnostica.hashCode() : 0);
        result = 31 * result + (linfonodos != null ? linfonodos.hashCode() : 0);
        result = 31 * result + (dataAtendimento != null ? dataAtendimento.hashCode() : 0);
        result = 31 * result + (animal != null ? animal.hashCode() : 0);
        result = 31 * result + (veterinario != null ? veterinario.hashCode() : 0);
        result = 31 * result + (cirurgia != null ? cirurgia.hashCode() : 0);
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 31 * result + (criadoEm != null ? criadoEm.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Prontuario setCodigo(LocalDateTime now) {
        this.codigo = createCodigo(now);
        return this;
    }

    public String getPrescricaoCodigo() {
        return codigo + "-prescricao";
    }

    public String prescricaoLatestVersion() {
        return String.valueOf(
                prescricoes.stream()
                        .mapToInt(Prescricao::getVersao)
                        .max()
                        .orElse(1));
    }
}

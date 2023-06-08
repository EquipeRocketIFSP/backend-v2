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

    private String regiaoColuna;

    private String regiaoAbdomen;

    private String regiaoMToracicos;

    private String regiaoMPelvicos;

    private boolean regiaoCabeca;

    private boolean regiaoTorax;

    private String regioesObs;

    private LocalDateTime dataAtendimento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_id", nullable = false)
    @ToString.Exclude
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @ToString.Exclude
    private Usuario veterinario;

    @OneToOne(mappedBy = "prontuario")
    @ToString.Exclude
    private Cirurgia cirurgia;

    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<Procedimento> procedimentos = new ArrayList<>();

    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<Exame> exames = new ArrayList<>();
    @Setter(AccessLevel.NONE)
    private String codigo;

    @ManyToOne
    @Accessors(chain = true)
    @JoinColumn(name = "tutor_id")
    @ToString.Exclude
    private Usuario tutor;

    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<Documento> documentos = new ArrayList<>();
    private Date criadoEm;

    @OneToMany
    @JoinTable(
            name = "prontuario_prescricoes",
            joinColumns = @JoinColumn(name = "prontuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "prescricao_id", referencedColumnName = "id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"prontuario_id", "prescricao_id"}) }
    )
    @ToString.Exclude
    private List<Prescricao> prescricoes;

    public Prontuario setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
        return this;
    }

    public void setProcedimentos(List<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
    }

    public static String createCodigo(LocalDateTime now) {
        // exemplo: VT-P-2022_12_03_02_19_20.pdf
        return "VT-P-" + now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
    }

    public List<Prescricao> getPrescricoes(int versao) {
        return prescricoes.stream()
                .filter(prescricao -> prescricao.getVersao() == versao)
                .toList();
    }

    final public String getMonthAtendimento() {
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
        if (!Objects.equals(apetite, that.apetite))
            return false;
        if (!Objects.equals(linfonodos, that.linfonodos))
            return false;
        if (!Objects.equals(linfonodosObs, that.linfonodosObs))
            return false;
        if (!Objects.equals(regiaoColuna, that.regiaoColuna))
            return false;
        if (!Objects.equals(regiaoAbdomen, that.regiaoAbdomen))
            return false;
        if (!Objects.equals(regiaoMToracicos, that.regiaoMToracicos))
            return false;
        if (!Objects.equals(regiaoMPelvicos, that.regiaoMPelvicos))
            return false;
        if (!Objects.equals(regioesObs, that.regioesObs))
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
        result = 31 * result + (regiaoColuna != null ? regiaoColuna.hashCode() : 0);
        result = 31 * result + (regiaoAbdomen != null ? regiaoAbdomen.hashCode() : 0);
        result = 31 * result + (regiaoMToracicos != null ? regiaoMToracicos.hashCode() : 0);
        result = 31 * result + (regiaoMPelvicos != null ? regiaoMPelvicos.hashCode() : 0);
        result = 31 * result + (regiaoCabeca ? 1 : 0);
        result = 31 * result + (regiaoTorax ? 1 : 0);
        result = 31 * result + (regioesObs != null ? regioesObs.hashCode() : 0);
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

    @Deprecated
    public Prontuario setCodigo(LocalDateTime now) {
        this.codigo = "VT-P-" + now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
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

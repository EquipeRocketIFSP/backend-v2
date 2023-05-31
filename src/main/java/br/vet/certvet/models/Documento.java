package br.vet.certvet.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance
@DiscriminatorColumn(
        name="tipo",
        discriminatorType = DiscriminatorType.STRING
)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(insertable = false, updatable = false)
    private String tipo;
    private String codigo;
    private Integer versao;
    private Date criadoEm;

    private String observacaoVet;
    private String observacaoTutor;
    private String causaMortis;
    private String causaMortisDescription;
    private String orientaDestinoCorpo;
    private String local;
    private LocalDateTime dataHoraObito;
    private String terapia;
    private String anestesia;
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Usuario veterinario;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    @ToString.Exclude
    private Clinica clinica;
    private String caminhoArquivo;

    protected String md5 = null;
    protected String etag = null;
    protected String algorithm = null;

    @ManyToMany
    @JoinTable(
            name = "assinadores_documentos",
            joinColumns = {@JoinColumn(name = "documento_id")},
            inverseJoinColumns = {@JoinColumn(name = "usuario_id")}
    )
    @ToString.Exclude
    protected List<Usuario> assinadores;

    @ManyToOne
    @JoinTable(name = "prontuario_documentos",
            joinColumns = { @JoinColumn(name = "documentos_id") },
            inverseJoinColumns = { @JoinColumn(name = "prontuario_id") },
            uniqueConstraints = { @UniqueConstraint(columnNames = {"documentos_id", "prontuario_id"}) }
    )
    private Prontuario prontuario;

    public Documento(LocalDateTime now) {
        setCodigo(now);
    }

    public Documento(LocalDateTime now, String tipo) {
        setCodigo(now);
        this.tipo = tipo;
    }
    public Documento setCodigo(LocalDateTime now) {
        // exemplo: VT-D-2022_12_03_02_19_20.pdf
        this.codigo = "VT-D-"+now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        return this;
    }

    public Documento setVeterinario(Usuario veterinario) {
        this.veterinario = veterinario;
        return this;
    }

    public Documento setClinica(Clinica clinica) {
        this.clinica = clinica;
        return this;
    }

    public Documento md5(String md5) {
        this.md5 = md5;
        return this;
    }

    public Documento etag(String etag) {
        this.etag = etag;
        return this;
    }

    public Documento algorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public Documento prontuario(Prontuario prontuario) {
        this.prontuario = prontuario;
        return this;
    }

    public Documento assinadores(List<Usuario> assinadores) {
        this.assinadores = assinadores;
        return this;
    }

    public Documento caminhoArquivo(String urlFullPath){
        this.caminhoArquivo = caminhoArquivo;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Documento documento = (Documento) o;

        if (!id.equals(documento.id)) return false;
        if (!Objects.equals(tipo, documento.tipo)) return false;
        if (!Objects.equals(codigo, documento.codigo)) return false;
        if (!Objects.equals(versao, documento.versao)) return false;
        if (!Objects.equals(criadoEm, documento.criadoEm)) return false;
        if (!Objects.equals(caminhoArquivo, documento.caminhoArquivo))
            return false;
        if (!Objects.equals(md5, documento.md5)) return false;
        if (!Objects.equals(etag, documento.etag)) return false;
        return Objects.equals(algorithm, documento.algorithm);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 31 * result + (versao != null ? versao.hashCode() : 0);
        result = 31 * result + (criadoEm != null ? criadoEm.hashCode() : 0);
        result = 31 * result + (caminhoArquivo != null ? caminhoArquivo.hashCode() : 0);
        result = 31 * result + (md5 != null ? md5.hashCode() : 0);
        result = 31 * result + (etag != null ? etag.hashCode() : 0);
        result = 31 * result + (algorithm != null ? algorithm.hashCode() : 0);
        return result;
    }
}

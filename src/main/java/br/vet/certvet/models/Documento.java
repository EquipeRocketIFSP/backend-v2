package br.vet.certvet.models;

import br.vet.certvet.models.especializacoes.AnestesiaDocumento;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
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

    private String tipo;
    private String caminho;

    private String codigo;
    private Integer versao;
    private Date criadoEm;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Usuario veterinario;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;
    private String caminhoArquivo;

    @Transient protected String titulo = null;
    @Transient protected String declaraConsentimento = null;
    @Transient protected String identificaAnimal = null;
    @Transient protected String declaraCienciaRiscos = null;
    @Transient protected String observacoesVeterinario = null;
    @Transient protected String observacoesResponsavel = null;
    @Transient protected String causaMortis = null;
    @Transient protected String orientaDestinoCorpo = null;
    @Transient protected String identificaResponsavel = null;
    @Transient protected String outrasObservacoes = null;
    @Transient protected String assinaturaResponsavel = null;
    @Transient protected String assinaturaVet = null;
    @Transient protected String explicaDuasVias = null;
    protected String md5 = null;

    protected String etag = null;

    protected String algorithm = null;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Prontuario prontuario;

    public Documento find(String documentoTipo) {
        return new AnestesiaDocumento().find(documentoTipo);
    }

    public String setCodigo(LocalDateTime now) {
        // exemplo: VT-D-2022_12_03_02_19_20.pdf
        this.codigo = "VT-D-"+now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        return this.codigo;
    }

    public Documento setVeterinario(Usuario veterinario) {
        this.veterinario = veterinario;
        return this;
    }

    public Documento setClinica(Clinica clinica) {
        this.clinica = clinica;
        return this;
    }

    public Documento setMd5(String md5) {
        this.md5 = md5;
        return this;
    }

    public Documento setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public Documento setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public Documento setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
        return this;
    }
}

package br.vet.certvet.models;

import br.vet.certvet.models.especializacoes.AnestesiaDocumento;
import lombok.*;
import lombok.experimental.Accessors;

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

    private String name;
    private Integer versao;
    private Date criadoEm;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Usuario veterinario;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;
    private String caminhoArquivo;

    protected String titulo = null;
    protected String declaraConsentimento = null;
    protected String identificaAnimal = null;
    protected String declaraCienciaRiscos = null;
    protected String observacoesVeterinario = null;
    protected String observacoesResponsavel = null;
    protected String causaMortis = null;
    protected String orientaDestinoCorpo = null;
    protected String identificaResponsavel = null;
    protected String outrasObservacoes = null;
    protected String assinaturaResponsavel = null;
    protected String assinaturaVet = null;
    protected String explicaDuasVias = null;

    protected String md5 = null;

    protected String etag = null;

    protected String algorithm = null;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Prontuario prontuario;

    public Documento find(String documentoTipo) {
        return new AnestesiaDocumento().find(documentoTipo);
    }

    public String setName(LocalDateTime now) {
        // exemplo: VT-D-2022_12_03_02_19_20.pdf
        this.name = "VT-D-"+now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        return this.name;
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

package br.vet.certvet.models;

import br.vet.certvet.models.especializacoes.AnestesiaDocumento;
import lombok.*;

import javax.persistence.*;
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

    @Setter
    protected byte[] pdf = null;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Prontuario prontuario;

    public Documento find(String documentoTipo) {
        return new AnestesiaDocumento().find(documentoTipo);
    }

    public Documento setProntuario(Prontuario p) {
        this.prontuario = p;
        return this;
    }
    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public void setVeterinario(Usuario veterinario) {
        this.veterinario = veterinario;
    }
}

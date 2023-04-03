package br.vet.certvet.models;

import br.vet.certvet.models.especializacoes.AnestesiaDocumento;
import lombok.*;

import javax.persistence.*;

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
}

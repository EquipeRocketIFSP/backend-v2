package br.vet.certvet.models;

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

    protected String cabecalho = null;
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
    protected String localData = null;
    protected String assinaturaResponsavel = null;
    protected String assinaturaVet = null;
    protected String explicaDuasVias = null;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Prontuario prontuario;
}

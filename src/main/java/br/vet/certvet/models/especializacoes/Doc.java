package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;
import lombok.Getter;
@Getter
public class Doc {

    protected Documento documento;
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
}

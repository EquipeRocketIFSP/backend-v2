package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;
import lombok.Getter;
@Getter
public class Doc {

    protected Documento documento;
    protected String titulo = "";
    protected String declaraConsentimento = "";
    protected String identificaAnimal = "";
    protected String declaraCienciaRiscos = "";
    protected String observacoesVeterinario = "";
    protected String observacoesResponsavel = "";
    protected String causaMortis = "";
    protected String orientaDestinoCorpo = "";
    protected String identificaResponsavel = "";
    protected String outrasObservacoes = "";
    protected String assinaturaResponsavel = "";
    protected String assinaturaVet = "";
    protected String explicaDuasVias = "";
}

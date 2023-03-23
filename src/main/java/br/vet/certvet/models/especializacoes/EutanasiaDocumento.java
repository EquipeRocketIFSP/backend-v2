package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

public class EutanasiaDocumento extends Documento {
    public EutanasiaDocumento(){
        this.cabecalho = "";
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO PARA REALIZAÇÃO DE EUTANÁSIA";
        this.declaraConsentimento = "Declaro estar ciente dos motivos que levam à necessidade de realização da eutanásia, que reconheço que esta é a opção escolhida por mim para cessar definitivamente o sofrimento do animal e, portanto, declaro o livre consentimento para a realização da eutanásia do animal abaixo identificado, a ser realizado pelo(a) Médico(a) Veterinário(a) ${veterinario.nome} CRMV-${veterinario.crmv}";
        this.identificaAnimal = "";
        this.declaraCienciaRiscos = "Declaro, ainda, que fui devidamente esclarecido(a) do método que será utilizado, assim como de que este é um processo irreversível.";
        this.observacoesVeterinario = "";
        this.observacoesResponsavel = "";
        this.causaMortis = null;
        this.orientaDestinoCorpo = null;
        this.identificaResponsavel = "";
        this.outrasObservacoes = null;
        this.localData = "";
        this.assinaturaResponsavel = "Assinatura do(a) responsável pelo animal";
        this.assinaturaVet = null;
        this.explicaDuasVias = "";
    }
}

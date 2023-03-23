package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

public class RetiraCorpoDocumento extends Documento {
    public RetiraCorpoDocumento(){
        this.cabecalho = "";
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO PARA RETIRADA DE CORPO DE ANIMAL EM ÓBITO";
        this.declaraConsentimento = "Declaro para os devidos fins, que, nesta ocasião, retiro o cadáver do animal abaixo identificado, que veio a óbito na localidade ${prontuario.obito.local}, às ${prontuario.obito.horas}, horas do dia (${prontuario.obito.data}), cujo óbito, provocado pela provável <b>causa mortis</b> ${prontuario.obito.causa} foi constatado pelo médico-veterinário que subscreve a presente, e que recebi esclarecimentos quanto à necessidade de dar tratamento respeitoso e destinação ambiental adequada ao cadáver, em respeito às normas ambientais.";
        this.identificaAnimal = "";
        this.declaraCienciaRiscos = null;
        this.observacoesVeterinario = "";
        this.observacoesResponsavel = "";
        this.causaMortis = null;
        this.orientaDestinoCorpo = null;
        this.identificaResponsavel = "";
        this.outrasObservacoes = null;
        this.localData = "";
        this.assinaturaResponsavel = "Assinatura do(a) responsável pelo animal <br/> Nome completo, RG e CPF";
        this.assinaturaVet = "ssinatura do(a) Médico(a) Veterinário(a) responsável pela constatação do óbito <br/> Nome e nº de inscrição no CRMV";
        this.explicaDuasVias = "";
    }
}

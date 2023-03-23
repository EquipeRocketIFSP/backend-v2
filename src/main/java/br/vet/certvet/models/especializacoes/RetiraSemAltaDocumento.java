package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

public class RetiraSemAltaDocumento extends Documento {
    public RetiraSemAltaDocumento(){
        this.cabecalho = "";
        this.titulo = "TERMO DE ESCLARECIMENTO PARA RETIRADA DE ANIMAL DO SERVIÇO VETERINÁRIO SEM ALTA MÉDICA";
        this.declaraConsentimento = "Declaro que foi esclarecido ao ora subscritor que o animal abaixo identificado não obteve alta médica e que há recomendação para manter o animal em internação em estabelecimento médico veterinário apropriado.";
        this.identificaAnimal = "";
        this.declaraCienciaRiscos = "Declaro ainda que estou ciente de que há riscos de agravamento da doença, inclusive morte, e que assumo inteira responsabilidade por esse ato.";
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

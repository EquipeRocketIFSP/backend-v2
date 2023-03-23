package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;
import br.vet.certvet.services.DocumentoService;
import org.springframework.stereotype.Service;

public class DoacaoPesquisaDocumento extends Documento {
    public DoacaoPesquisaDocumento(){
        this.cabecalho = "";
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO DE DOAÇÃO DE CORPO DE ANIMAL PARA FINS DE ENSINO E PESQUISA";
        this.declaraConsentimento = "Declaro o livre consentimento sobre a doação do corpo do animal abaixo identificado.";
        this.identificaAnimal = "";
        this.declaraCienciaRiscos = "Declaro, ainda, ter sido esclarecido(a) acerca da destinação do corpo para fins de estudo e pesquisa.";
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

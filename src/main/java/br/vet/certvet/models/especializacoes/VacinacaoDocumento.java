package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

public class VacinacaoDocumento extends Documento {
    public VacinacaoDocumento(){
        this.cabecalho = "";
        this.titulo = "ATESTADO DE VACINAÇÃO";
        this.declaraConsentimento = "Atesto para os devidos fins, que o animal abaixo identificado foi vacinado por mim nesta data, conforme informações abaixo:";
        this.identificaAnimal = "";
        this.declaraCienciaRiscos = null;
        this.observacoesVeterinario = null;
        this.observacoesResponsavel = null;
        this.causaMortis = null;
        this.orientaDestinoCorpo = null;
        this.identificaResponsavel = "";
        this.outrasObservacoes = null;
        this.localData = "";
        this.assinaturaResponsavel = null;
        this.assinaturaVet = "Assinatura do(a) Médico(a) Veterinário(a) <br/> Nome e nº de inscrição no CRMV";
        this.explicaDuasVias = "";
    }
}

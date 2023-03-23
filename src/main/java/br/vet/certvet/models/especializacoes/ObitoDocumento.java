package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

public class ObitoDocumento extends Documento {
    public ObitoDocumento(){
        this.cabecalho = "";
        this.titulo = "ATESTADO DE ÓBITO";
        this.declaraConsentimento = "Atesto para os devidos fins que o animal abaixo identificado veio a óbito na localidade ${prontuario.obito.local}, às ${prontuario.obito.horas}, horas do dia (${prontuario.obito.data}), sendo a provável causa mortis ${prontuario.obito.causa}.";
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

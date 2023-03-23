package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

public class TerapeuticoDocumento extends Documento {
    public TerapeuticoDocumento(){
        this.cabecalho = "";
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO PARA REALIZAÇÃO DE PROCEDIMENTO TERAPÊUTICO DE RISCO";
        this.declaraConsentimento = "Declaro o livre consentimento para a realização do(s) procedimento(s) terapêutico(s) de risco ${prontuario.terapias} no animal abaixo identificado, a ser realizado pelo(a) Médico(a) Veterinário(a) ${veterinario.nome} CRMV-${veterinario.crmv}.";
        this.identificaAnimal = "";
        this.declaraCienciaRiscos = "Declaro, ainda, ter sido esclarecido(a) acerca dos possíveis riscos inerentes, durante ou após a realização do(s) procedimento(s) terapêutico(s), estando o referido o(a) profissional isento(a) de quaisquer responsabilidades decorrentes de tais riscos.";
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

package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

final public class ExameDocumento extends Documento {

    public ExameDocumento(){
        this.cabecalho = "";
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO PARA REALIZAÇÃO DE EXAMES";
        this.declaraConsentimento = "Declaro o livre consentimento para a realização do(s) exame(s) ${prontuario.exames} no animal abaixo identificado, a ser realizado pelo(a) Médico(a) Veterinário(a) ${veterinario.nome} CRMV-${veterinario.crmv} =";
        this.identificaAnimal = "";
        this.declaraCienciaRiscos = "Declaro, ainda, ter sido esclarecido(a) acerca dos possíveis riscos inerentes ao procedimento, durante ou após a realização do(s) citado(s) exame(s), estando o(a) referido(a) profissional isento(a) de quaisquer responsabilidades decorrentes de tais riscos.";
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

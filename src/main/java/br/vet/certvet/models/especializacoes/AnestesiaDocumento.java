package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;
import org.springframework.stereotype.Service;

public class AnestesiaDocumento extends Documento {
    public AnestesiaDocumento(){
        this.cabecalho = "";
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO PARA REALIZAÇÃO DE PROCEDIMENTOS ANESTÉSICOS";
        this.declaraConsentimento = "Declaro o livre consentimento para a realização do(s) procedimento(s) anestésico(s) necessário(s) no animal abaixo identificado, a ser realizado pelo(a) Médico(a) Veterinário(a) ${veterinario.nome} CRMV-${veterinario.crmv}.";
        this.identificaAnimal = "";
        this.declaraCienciaRiscos = "Declaro, ainda, ter sido esclarecido(a) acerca dos possíveis riscos, inerentes ao(s) procedimento(s) proposto(s), estando o(a) referido(a) profissional isento(a) de quaisquer responsabilidades decorrentes de tais riscos. Tipo de procedimento Anestésico indicado: ${prontuario.anestesia}.";
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

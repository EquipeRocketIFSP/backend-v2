package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

final public class SanitarioDocumento extends Documento {
    public SanitarioDocumento(){
        this.cabecalho = "";
        this.titulo = "ATESTADO SANITÁRIO";
        this.declaraConsentimento = "Atesto para os devidos fins que foi por mim examinado nesta data o animal abaixo identificado, o qual apresentou bom estado geral de saúde durante o exame clínico, e que se encontram atendidas as medidas sanitárias definidas pelo(s) Serviço(s) Médico-Veterinário(s) Oficial(is), quando aplicável:";
        this.identificaAnimal = "";
        this.declaraCienciaRiscos = null;
        this.observacoesVeterinario = null;
        this.observacoesResponsavel = null;
        this.causaMortis = "";
        this.orientaDestinoCorpo = "";
        this.identificaResponsavel = "";
        this.outrasObservacoes = "";
        this.localData = "";
        this.assinaturaResponsavel = null;
        this.assinaturaVet = "Assinatura do(a) Médico(a) Veterinário(a) <br/> Nome e nº de inscrição no CRMV";
        this.explicaDuasVias = null;
    }
}

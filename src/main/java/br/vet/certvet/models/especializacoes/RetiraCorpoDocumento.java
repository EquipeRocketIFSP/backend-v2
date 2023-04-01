package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

public class RetiraCorpoDocumento extends Documento {
    public RetiraCorpoDocumento(){
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO PARA RETIRADA DE CORPO DE ANIMAL EM ÓBITO";
        this.declaraConsentimento = "Declaro para os devidos fins, que, nesta ocasião, retiro o cadáver do animal abaixo identificado, que veio a óbito na localidade ${prontuario.obito.local}, às ${prontuario.obito.horas}, horas do dia (${prontuario.obito.data}), cujo óbito, provocado pela provável <b>causa mortis</b> ${prontuario.obito.causa} foi constatado pelo médico-veterinário que subscreve a presente, e que recebi esclarecimentos quanto à necessidade de dar tratamento respeitoso e destinação ambiental adequada ao cadáver, em respeito às normas ambientais.";
        this.observacoesVeterinario = """
                <div id="observacoes_veterinario">
                        <p>
                            Observações de interesse a serem fornecidas pelo(a) Médico(a) Veterinário(a):
                        </p>
                        <span>${documento.observacaoVet}</span>
                    </div>""".indent(4);
        this.observacoesResponsavel = """
                <div id="observacoes_responsavel">
                        <p>
                            Observações de interesse a serem fornecidas pelo(a) tutor(a)/proprietário(a)/responsável:
                        </p>
                        <span>${documento.observacaoTutor}</span>
                    </div>
                """.indent(4);
        this.assinaturaResponsavel = """
                <div id="assinatura_responsavel">
                    <hr>
                    <p>Assinatura do(a) responsável pelo animal <br/> Nome completo, RG e CPF</p>
                </div>""".indent(4);
        this.assinaturaVet = """
                <div id="assinatura_vet">
                    <hr>
                    <p>Assinatura do(a) Médico(a) Veterinário(a) responsável pela constatação do óbito <br/> Nome e nº de inscrição no CRMV</p>
                </div>""".indent(4);
        this.explicaDuasVias = """
                <div id="explica_duas_vias">
                    <span>
                        (documento a ser emitido em 2 vias: 1ª via: médico-veterinário; 2ª via: proprietário/tutor)
                    </span>
                </div>""".indent(4);
    }
    @Override
    public Documento find(String tipoDocumento){
        return  "retiraCorpo".equals(tipoDocumento)
                ? this
                : new RetiraSemAltaDocumento().find(tipoDocumento);
    }
}

package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

public class ObitoDocumento extends Documento {
    public ObitoDocumento(){
        this.titulo = "ATESTADO DE ÓBITO";
        this.declaraConsentimento = "Atesto para os devidos fins que o animal abaixo identificado veio a óbito na localidade ${prontuario.obito.local}, às ${prontuario.obito.horas}, horas do dia (${prontuario.obito.data}), sendo a provável causa mortis ${prontuario.obito.causa}.";
        this.causaMortis = """
                <div id="causaMortis">
                    <p>
                        Outras informações complementares à provável causa mortis e informação de ter sido feita a notificação obrigatória quando for o caso:
                    </p>
                    <span>${documento.causaMortis}</span>
                </div>""".indent(4);
        this.orientaDestinoCorpo = """
                <div id="orientaDestinoCorpo">
                    <p>
                        Orientações para destinação do corpo animal (aspectos sanitários e ambientais):
                    </p>
                    <span>${documento.orientaDestinoCorpo}</span>
                </div>""".indent(4);
        this.outrasObservacoes = """
                <div id="outrasObservacoes">
                    <p>
                        Outras observações:
                    </p>
                    <span>${documento.outrasObservacoes}</span>
                </div>""".indent(4);
        this.assinaturaVet =  """
                <div id="assinatura_responsavel">
                    <hr>
                    <p>Assinatura do(a) Médico(a) Veterinário(a) <br/> Nome e nº de inscrição no CRMV</p>
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
        return  "obito".equals(tipoDocumento)
                ? this
                : new RetiraCorpoDocumento().find(tipoDocumento);
    }
}

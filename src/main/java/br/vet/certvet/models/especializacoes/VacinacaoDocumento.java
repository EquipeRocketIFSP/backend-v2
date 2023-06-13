package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;


public class VacinacaoDocumento extends Doc {
    public VacinacaoDocumento(Documento documento){
        super.documento = documento;
        this.titulo = "ATESTADO DE VACINAÇÃO";
        this.declaraConsentimento = "Atesto para os devidos fins, que o animal abaixo identificado foi vacinado por mim nesta data, conforme informações abaixo:";
        this.assinaturaVet = """
                <div id="assinatura_vet">
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
}

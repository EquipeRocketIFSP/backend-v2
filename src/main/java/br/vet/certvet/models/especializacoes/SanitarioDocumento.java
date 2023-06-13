package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;


public class SanitarioDocumento extends Doc {
    public SanitarioDocumento(Documento documento){
        super.documento = documento;
        super.titulo = "ATESTADO SANITÁRIO";
        super.declaraConsentimento = "Atesto para os devidos fins que foi por mim examinado nesta data o animal abaixo identificado, o qual apresentou bom estado geral de saúde durante o exame clínico, e que se encontram atendidas as medidas sanitárias definidas pelo(s) Serviço(s) Médico-Veterinário(s) Oficial(is), quando aplicável:";
        super.assinaturaVet = """
                <div id="assinatura_responsavel">
                    <hr>
                    <p>Assinatura do(a) responsável pelo animal</p>
                </div>""".indent(4);
    }
}

package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

final public class SanitarioDocumento extends Documento {
    public SanitarioDocumento(){
        this.titulo = "ATESTADO SANITÁRIO";
        this.declaraConsentimento = "Atesto para os devidos fins que foi por mim examinado nesta data o animal abaixo identificado, o qual apresentou bom estado geral de saúde durante o exame clínico, e que se encontram atendidas as medidas sanitárias definidas pelo(s) Serviço(s) Médico-Veterinário(s) Oficial(is), quando aplicável:";
        this.assinaturaVet = """
                <div id="assinatura_responsavel">
                    <hr>
                    <p>Assinatura do(a) responsável pelo animal</p>
                </div>""".indent(4);
    }
    @Override
    public Documento find(String tipoDocumento){
        return  "sanitario".equals(tipoDocumento)
                ? this
                : new TerapeuticoDocumento().find(tipoDocumento);
    }
}

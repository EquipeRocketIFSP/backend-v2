package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

public class RetiraSemAltaDocumento extends Doc {
    public RetiraSemAltaDocumento(Documento documento){
        super.documento = documento;
        this.titulo = "TERMO DE ESCLARECIMENTO PARA RETIRADA DE ANIMAL DO SERVIÇO VETERINÁRIO SEM ALTA MÉDICA";
        this.declaraConsentimento = "Declaro que foi esclarecido ao ora subscritor que o animal abaixo identificado não obteve alta médica e que há recomendação para manter o animal em internação em estabelecimento médico veterinário apropriado.";
        this.declaraCienciaRiscos = """
                <div id="declara_ciencia_riscos">
                        <p>
                            Declaro ainda que estou ciente de que há riscos de agravamento da doença, inclusive morte, e que assumo inteira responsabilidade por esse ato.
                        </p>
                    </div>""".indent(4);
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
                    <p>Assinatura do(a) responsável pelo animal</p>
                </div>""".indent(4);
        this.explicaDuasVias = """
                <div id="explica_duas_vias">
                    <span>
                        (documento a ser emitido em 2 vias: 1ª via: médico-veterinário; 2ª via: proprietário/tutor)
                    </span>
                </div>""".indent(4);
    }
}

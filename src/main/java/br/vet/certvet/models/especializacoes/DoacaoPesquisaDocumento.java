package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;
import br.vet.certvet.services.DocumentoService;
import org.springframework.stereotype.Service;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;


public class DoacaoPesquisaDocumento extends Doc {
    public DoacaoPesquisaDocumento(Documento documento){
        super.documento = documento;
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO DE DOAÇÃO DE CORPO DE ANIMAL PARA FINS DE ENSINO E PESQUISA";
        this.declaraConsentimento = "Declaro o livre consentimento sobre a doação do corpo do animal abaixo identificado.";
        this.declaraCienciaRiscos = """
                <div id="declara_ciencia_riscos">
                        <p>
                            Declaro, ainda, ter sido esclarecido(a) acerca da destinação do corpo para fins de estudo e pesquisa.
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

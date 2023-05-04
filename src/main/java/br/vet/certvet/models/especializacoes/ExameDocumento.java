package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Exame")
public class ExameDocumento extends Documento {

    public ExameDocumento(){
        super(LocalDateTime.now());
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO PARA REALIZAÇÃO DE EXAMES";
        this.declaraConsentimento = "Declaro o livre consentimento para a realização do(s) exame(s) ${prontuario.exames} no animal abaixo identificado, a ser realizado pelo(a) Médico(a) Veterinário(a) ${veterinario.nome} CRMV-${veterinario.crmv} =";
        this.declaraCienciaRiscos = """
                <div id="declara_ciencia_riscos">
                        <p>
                            Declaro, ainda, ter sido esclarecido(a) acerca dos possíveis riscos inerentes ao procedimento, durante ou após a realização do(s) citado(s) exame(s), estando o(a) referido(a) profissional isento(a) de quaisquer responsabilidades decorrentes de tais riscos.
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
    @Override
    public Documento find(String tipoDocumento){
        return  "exame".equals(tipoDocumento)
                ? this
                : new ObitoDocumento().find(tipoDocumento);
    }
}

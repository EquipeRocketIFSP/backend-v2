package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Documento;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Terapeutico")
public class TerapeuticoDocumento extends Documento {
    public TerapeuticoDocumento(){
        super(LocalDateTime.now());
        this.titulo = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO PARA REALIZAÇÃO DE PROCEDIMENTO TERAPÊUTICO DE RISCO";
        this.declaraConsentimento = "Declaro o livre consentimento para a realização do(s) procedimento(s) terapêutico(s) de risco ${prontuario.terapias} no animal abaixo identificado, a ser realizado pelo(a) Médico(a) Veterinário(a) ${veterinario.nome} CRMV-${veterinario.crmv}.";
        this.declaraCienciaRiscos = """
                <div id="declara_ciencia_riscos">
                        <p>
                            Declaro, ainda, ter sido esclarecido(a) acerca dos possíveis riscos inerentes, durante ou após a realização do(s) procedimento(s) terapêutico(s), estando o referido o(a) profissional isento(a) de quaisquer responsabilidades decorrentes de tais riscos.
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
    @Override
    public Documento find(String tipoDocumento){
        return  "terapeutico".equals(tipoDocumento)
                ? this
                : new TratamentoClinicoDocumento().find(tipoDocumento);
    }
}

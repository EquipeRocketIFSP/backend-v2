package br.vet.certvet.models.especializacoes;

import br.vet.certvet.models.Prescricao;

import java.util.List;

public class PrescricaoDocumento {

    private PrescricaoDocumento(){}
    private static final String DADOS_MEDICOS = """
        <div class="dadosMedicos">
            <p class="posologia"><b>Medicamento: ${prescricao.nome_medicamento_prescrito}</b></p>
            <p>Uso: ${prescricao.uso}</p>
            <p>Dose: ${prescricao.dose}</p>
            <p>Forma farmacêutica: ${prescricao.forma_farmaceutica}</p>
            <p>Concentração: ${prescricao.concentracao}</p>
            <p>Frequencia: ${prescricao.frequencia}</p>
            <p>Duração: ${prescricao.duracao}</p>
            <p>Quando aplicar: ${prescricao.quando_aplicar}</p>
            <p>Observações: ${prescricao.observacoes}</p>
        </div>
    """.indent(4);

    public static String getDivLayout(List<Prescricao> prescricoes) {
        return prescricoes.stream()
                .map(prescricao -> DADOS_MEDICOS)
                .toList()
                .toString();
    }
}

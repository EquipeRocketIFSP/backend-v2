package br.vet.certvet.models.especializacoes;

import br.vet.certvet.exceptions.EscritaPrescricaoPdfException;
import br.vet.certvet.models.Prescricao;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.text.StringSubstitutor;

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
    """;

    public static String getDivLayout(List<Prescricao> prescricoes) {
        return prescricoes.stream()
                .map(prescricao -> new StringSubstitutor(
                        ImmutableMap.<String, String>builder()
                                .put("prescricao.nome_medicamento_prescrito", prescricao.getNome())
                                .put("prescricao.uso", prescricao.getUso())
                                .put("prescricao.dose", prescricao.getDose())
                                .put("prescricao.forma_farmaceutica", prescricao.getFormaFarmaceutica())
                                .put("prescricao.concentracao", prescricao.getConcentracao())
                                .put("prescricao.frequencia", prescricao.getFrequencia())
                                .put("prescricao.duracao", prescricao.getDuracao())
                                .put("prescricao.quando_aplicar", prescricao.getQuandoAplicar())
                                .put("prescricao.observacoes", prescricao.getObservacoes())
                                .build()
                ).replace(DADOS_MEDICOS))
                .reduce((s, s2) -> s + "\n" + s2)
                .orElseThrow(() -> new EscritaPrescricaoPdfException("Não foi possível adicionar os medicamentos prescritos no receituário. Verifique se existe algum campo faltando"));
    }
}

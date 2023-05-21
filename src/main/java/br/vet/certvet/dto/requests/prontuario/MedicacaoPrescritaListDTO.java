package br.vet.certvet.dto.requests.prontuario;

import br.vet.certvet.models.Prescricao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MedicacaoPrescritaListDTO extends ProntuarioDTO {
    @JsonProperty("medicacoes_utilizadas")
    private List<MedicacaoPrescritaDTO> medicacoesUtilizadas;

    public MedicacaoPrescritaListDTO of(List<MedicacaoPrescritaDTO> prescricoes) {
        medicacoesUtilizadas = prescricoes;
        return this;
    }

    public List<Prescricao> translate() {
        return medicacoesUtilizadas.stream()
                .map(MedicacaoPrescritaDTO::translate)
                .toList();
    }
}

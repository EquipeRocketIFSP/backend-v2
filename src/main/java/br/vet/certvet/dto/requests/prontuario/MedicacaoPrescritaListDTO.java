package br.vet.certvet.dto.requests.prontuario;

import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Prontuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MedicacaoPrescritaListDTO extends ProntuarioDTO {
    @JsonProperty("medicacoes_prescritas")
    private List<MedicacaoPrescritaDTO> medicacoesPrescritas;

    public MedicacaoPrescritaListDTO of(List<MedicacaoPrescritaDTO> medicacoesPrescritas) {
        this.medicacoesPrescritas = medicacoesPrescritas;
        return this;
    }

    public List<Prescricao> translate(Prontuario prontuario) {
        return medicacoesPrescritas.stream()
                .map(i -> new MedicacaoPrescritaDTO().translate(prontuario))
                .toList();
    }
}

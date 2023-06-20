package br.vet.certvet.dto.requests.prontuario;

import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Prontuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MedicacaoPrescritaListDTO extends ProntuarioDTO {
    @JsonProperty("medicacoes_prescritas")
    private List<MedicacaoPrescritaDTO> medicacoesPrescritas;

    public MedicacaoPrescritaListDTO of(Prontuario prontuario) {
        final int max = prontuario.getPrescricoes()
                .stream()
                .mapToInt(Prescricao::getVersao)
                .max()
                .orElse(1);
        return (MedicacaoPrescritaListDTO) this.setMedicacoesPrescritas(
                prontuario.getPrescricoes()
                        .stream()
                        .filter(prescricao -> prescricao.getDataExclusao() == null)
                        .filter(prescricao -> prescricao.getVersao() == max)
                        .map(p -> new MedicacaoPrescritaDTO().of(p))
                        .toList()
                )
                .setAnimal(prontuario.getAnimal().getId())
                .setTutor(prontuario.getTutor().getId())
                .setVeterinario(prontuario.getVeterinario().getId())
                ;
    }

    public List<Prescricao> translate(Prontuario prontuario) {
        return medicacoesPrescritas.stream()
                .map(i -> new MedicacaoPrescritaDTO().translate(prontuario))
                .toList();
    }
}

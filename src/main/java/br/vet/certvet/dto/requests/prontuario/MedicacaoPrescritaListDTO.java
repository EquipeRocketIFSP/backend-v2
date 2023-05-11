package br.vet.certvet.dto.requests.prontuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MedicacaoPrescritaListDTO extends ProntuarioDTO {
    @JsonProperty("medicacoes_utilizadas")
    private List<MedicacaoPrescritaDTO> medicacoesUtilizadas;
}

package br.vet.certvet.dto.requests.prontuario.exame;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class ExameListDTO extends ProntuarioDTO {
    private List<ExameDTO> exames;
}

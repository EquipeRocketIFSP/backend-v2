package br.vet.certvet.dto.requests.prontuario.procedimento;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class ProcedimentoListDTO extends ProntuarioDTO {
    private List<ProcedimentoDTO> procedimentos;
}

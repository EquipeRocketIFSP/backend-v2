package br.vet.certvet.models.factories;

import br.vet.certvet.dto.requests.prontuario.procedimento.ProcedimentoDTO;
import br.vet.certvet.models.*;
import br.vet.certvet.models.mappers.ProcedimentoDTOMapper;

public class ProcedimentoFactory {
    public static Procedimento factory(ProcedimentoDTO dto, Prontuario prontuario) {
        return ProcedimentoDTOMapper.assignToModel(dto, new Procedimento(prontuario));
    }
}

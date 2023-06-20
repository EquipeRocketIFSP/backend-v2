package br.vet.certvet.models.mappers;

import br.vet.certvet.dto.requests.prontuario.procedimento.ProcedimentoDTO;
import br.vet.certvet.models.Procedimento;

public class ProcedimentoDTOMapper {

    private ProcedimentoDTOMapper(){}
    public static Procedimento assignToModel(ProcedimentoDTO dto, Procedimento model) {
        model.setOutros(dto.getProcedimentoOutros());

        return model;
    }
}

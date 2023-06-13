package br.vet.certvet.models.mappers;

import br.vet.certvet.dto.requests.prontuario.cirurgia.CirurgiaDTO;
import br.vet.certvet.models.Cirurgia;

public class CirurgiaDTOMapper {

    private CirurgiaDTOMapper(){}
    public static Cirurgia assignToModel(CirurgiaDTO dto, Cirurgia model) {
        return model.setDescricao(dto.getDescricao())
                .setData(dto.getData());
    }
}

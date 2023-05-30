package br.vet.certvet.models.mappers;

import br.vet.certvet.dto.requests.prontuario.cirurgia.CirurgiaDTO;
import br.vet.certvet.models.Cirurgia;

public class CirurgiaDTOMapper {
    public static Cirurgia assignToModel(CirurgiaDTO dto, Cirurgia model) {
        model.setDescricao(dto.getDescricao())
                .setData(dto.getData());

        return model;
    }
}

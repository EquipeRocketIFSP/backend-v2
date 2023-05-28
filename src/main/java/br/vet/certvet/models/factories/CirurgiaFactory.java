package br.vet.certvet.models.factories;

import br.vet.certvet.dto.requests.prontuario.cirurgia.CirurgiaDTO;
import br.vet.certvet.models.*;
import br.vet.certvet.models.mappers.CirurgiaDTOMapper;

public class CirurgiaFactory {
    public static Cirurgia factory(CirurgiaDTO dto, Prontuario prontuario) {
        return CirurgiaDTOMapper.assignToModel(dto, new Cirurgia(prontuario));
    }
}

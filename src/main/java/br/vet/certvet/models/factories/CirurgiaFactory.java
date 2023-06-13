package br.vet.certvet.models.factories;

import br.vet.certvet.dto.requests.prontuario.cirurgia.CirurgiaDTO;
import br.vet.certvet.models.Cirurgia;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.mappers.CirurgiaDTOMapper;

public class CirurgiaFactory {
    private CirurgiaFactory(){}
    public static Cirurgia factory(CirurgiaDTO dto, Prontuario prontuario) {
        return CirurgiaDTOMapper.assignToModel(dto, new Cirurgia(prontuario));
    }
}

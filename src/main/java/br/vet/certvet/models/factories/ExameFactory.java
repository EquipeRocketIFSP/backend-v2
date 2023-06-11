package br.vet.certvet.models.factories;

import br.vet.certvet.dto.requests.prontuario.exame.ExameDTO;
import br.vet.certvet.models.*;
import br.vet.certvet.models.mappers.ExameDTOMapper;

public class ExameFactory {
    public static Exame factory(ExameDTO dto, Prontuario prontuario) {
        return ExameDTOMapper.assignToModel(dto, new Exame(prontuario));
    }
}

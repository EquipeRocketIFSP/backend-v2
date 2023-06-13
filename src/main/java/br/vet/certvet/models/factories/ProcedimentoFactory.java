package br.vet.certvet.models.factories;

import br.vet.certvet.dto.requests.prontuario.procedimento.ProcedimentoDTO;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.Procedimento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.mappers.ProcedimentoDTOMapper;

public class ProcedimentoFactory {

    private ProcedimentoFactory(){}
    public static Procedimento factory(ProcedimentoDTO dto, Prontuario prontuario) {
        return ProcedimentoDTOMapper.assignToModel(dto, new Procedimento(prontuario));
    }

    public static Procedimento factory(ProcedimentoDTO dto, Estoque estoque, Prontuario prontuario) {
        Procedimento procedimento = new Procedimento(prontuario)
                .setMedicamentoConsumido(estoque)
                .setDoseMedicamento(dto.getDose());

        return ProcedimentoDTOMapper.assignToModel(dto, procedimento);
    }
}

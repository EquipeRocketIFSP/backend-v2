package br.vet.certvet.dto.responses;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.models.Medicamento;

public class MedicamentoResponseDto extends MedicamentoRequestDto {
    public Long id;

    public MedicamentoResponseDto(Medicamento medicamento) {
        this.id = medicamento.getId();
        this.nome = medicamento.getNome();
        this.codigoRegistro = medicamento.getCodigoRegistro();
        this.principioAtivo = medicamento.getPrincipioAtivo();
        this.viaUso = medicamento.getViaUso();
        this.dose = medicamento.getDose();
        this.concentracao = medicamento.getConcentracao();
        this.intervaloDose = medicamento.getIntervaloDose();
    }
}

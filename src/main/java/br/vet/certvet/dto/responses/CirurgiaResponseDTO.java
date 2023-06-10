package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Cirurgia;
import br.vet.certvet.models.Estoque;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class CirurgiaResponseDTO {
    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("data")
    private String data;

    @JsonProperty("medicamentos")
    private List<CirurgiaMedicamentos> medicamentos;

    public CirurgiaResponseDTO(Cirurgia cirurgia) {
        this.descricao = cirurgia.getDescricao();
        this.data = cirurgia.getData().toString();

        this.medicamentos = cirurgia.getMedicamentosConsumidos().stream().map(cirurgiaEstoqueMedicamento -> {
            Estoque estoque = cirurgiaEstoqueMedicamento.getEstoque();
            BigDecimal dose = cirurgiaEstoqueMedicamento.getDose();

            return new CirurgiaMedicamentos(estoque, dose);
        }).toList();
    }
}

class CirurgiaMedicamentos {
    @JsonProperty("dose")
    private BigDecimal dose;

    @JsonProperty("lote")
    private EstoqueResponseDto lote;

    @JsonProperty("medicamento")
    private MedicamentoResponseDto medicamento;

    public CirurgiaMedicamentos(Estoque estoque, BigDecimal dose) {
        this.dose = dose;
        this.lote = new EstoqueResponseDto(estoque);
        this.medicamento = new MedicamentoResponseDto(estoque.getMedicamento());
    }
}

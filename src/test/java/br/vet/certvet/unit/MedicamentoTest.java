package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.repositories.MedicamentoRespository;
import br.vet.certvet.services.implementation.MedicamentoServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class MedicamentoTest {
    @Autowired
    private MedicamentoServiceImpl medicamentoService;

    @Autowired
    private MedicamentoRespository medicamentoRespository;

    @AfterEach
    public void truncateTable() {
        this.medicamentoRespository.deleteAll();
    }

    @Test
    public void createMedicamento() {
        Medicamento medicamento = this.medicamentoService.create(MedicamentoTest.factoryMedicamentoDto());

        assertThat(medicamento).isNotNull();
        assertThat(medicamento.getId()).isNotNull();
    }

    public static MedicamentoRequestDto factoryMedicamentoDto() {
        MedicamentoRequestDto dto = new MedicamentoRequestDto();

        dto.nome = "ibuprofeno";
        dto.principioAtivo = "IBUPROFENO";
        dto.codigoRegistro = "103920201";
        dto.viaUso = "Oral";
        dto.dose = "1mg";
        dto.concentracao = "1mg/kg";
        dto.intervaloDose = "15 min";

        return dto;
    }
}

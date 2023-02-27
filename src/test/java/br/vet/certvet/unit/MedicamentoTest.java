package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.repositories.MedicamentoRespository;
import br.vet.certvet.services.implementation.MedicamentoServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
public class MedicamentoTest {
    private MedicamentoRespository medicamentoRepository = mock(MedicamentoRespository.class);
    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    @AfterEach
    public void truncateTable() {
//        this.medicamentoRespository.deleteAll();
    }

    @Test
    void createMedicamento() {
        var dto = MedicamentoTest.factoryMedicamentoDto();
        Medicamento parametro = new Medicamento(dto);
        when(medicamentoRepository.findByCodigoRegistro(dto.codigoRegistro)).thenReturn(Optional.empty());
        when(medicamentoRepository.saveAndFlush(any())).thenReturn(parametro);
        Medicamento medicamento = this.medicamentoService.create(dto);

        assertEquals(parametro, medicamento);
    }

    @Test
    void createDuplicateMedicamento() {
        var dto = MedicamentoTest.factoryMedicamentoDto();
        Medicamento parametro = new Medicamento(dto);
        when(medicamentoRepository.findByCodigoRegistro(dto.codigoRegistro)).thenReturn(Optional.of(parametro));

        Exception e = assertThrows(
                ConflictException.class,
                () -> medicamentoService.create(MedicamentoTest.factoryMedicamentoDto())
        );
        assertEquals(e.getMessage(), "Medicamento já existe");
    }

    @Test
    void findExistingMedicamento() {
        /*
        Instancia simulação de medicamento existente e prepara retorno da repository
         */
        var dto = MedicamentoTest.factoryMedicamentoDto();
        Medicamento parametro = new Medicamento(dto);
        when(medicamentoRepository.findByCodigoRegistro(dto.codigoRegistro)).thenReturn(Optional.empty());
        when(medicamentoRepository.saveAndFlush(any())).thenReturn(parametro);
        this.medicamentoService.create(dto);

        /*
        Especifica resposta da repository quando ocorrer consulta
         */
        when(medicamentoRepository.findById(parametro.getId())).thenReturn(Optional.of(parametro));
        Medicamento medicamento = this.medicamentoService.findOne(parametro.getId());

        assertNotNull(medicamento);
        assertEquals(parametro,medicamento);
    }

    @Test
    void findUnexistentMedicamento() {
        assertThrowsExactly(NotFoundException.class, () -> {
            this.medicamentoService.findOne(Long.parseLong("9999999"));
        });
    }

    private static MedicamentoRequestDto factoryMedicamentoDto() {
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

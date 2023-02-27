package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.ClinicaRequestDto;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.services.ClinicaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
public class ClinicaTest {
    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private ClinicaService clinicaService;

    @AfterEach
    public void truncateTable() {
        this.clinicaRepository.deleteAll();
    }

    @Test
    public void createClinica() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();
        Clinica clinica = this.clinicaService.create(dto);

        final Clinica CLINICA_COMPARATION = new Clinica(dto);

        assertThat(clinica).isNotNull();
        assertThat(clinica.getId()).isNotNull();

        assertThat(clinica)
                .usingRecursiveComparison()
                .ignoringFields("id", "usuarios", "agendamentos", "prontuarios")
                .isEqualTo(CLINICA_COMPARATION);
    }

    @Test
    public void getExistentClinica() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();

        Clinica clinicaTest = this.clinicaService.create(dto);
        Clinica clinica = this.clinicaService.findById(clinicaTest.getId());

        assertThat(clinica).isEqualTo(clinicaTest);
    }

    @Test
    public void getExistentClinicaByCnpj() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();

        Clinica clinicaTest = this.clinicaService.create(dto);
        Clinica clinica = this.clinicaService.findByCnpj(clinicaTest.getCnpj());

        assertThat(clinica).isEqualTo(clinicaTest);
    }

    @Test
    public void getNotExistentClinica() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();

        this.clinicaService.create(dto);

        assertThrowsExactly(NotFoundException.class, () -> this.clinicaService.findById(Long.parseLong("999999999")));
    }

    @Test
    public void getNotExistentClinicaByCnpj() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();

        this.clinicaService.create(dto);

        assertThrowsExactly(NotFoundException.class, () -> this.clinicaService.findByCnpj("11.222.333/0001-11"));
    }

    @Test
    public void editClinica() {
        Clinica clinica = this.clinicaService.create(ClinicaTest.factoryClinicaInicialRequestDto());
        ClinicaRequestDto dto = ClinicaTest.factoryUpdatedClinicaDto();

        final Clinica CLINICA_COMPARATION = new Clinica(ClinicaTest.factoryClinicaInicialRequestDto());
        CLINICA_COMPARATION.fill(dto);

        clinica = this.clinicaService.edit(dto, clinica);

        assertThat(clinica)
                .usingRecursiveComparison()
                .ignoringFields("id", "usuarios", "agendamentos", "prontuarios")
                .isEqualTo(CLINICA_COMPARATION);
    }

    public static ClinicaInicialRequestDto factoryClinicaInicialRequestDto() {
        ClinicaInicialRequestDto dto = new ClinicaInicialRequestDto();

        dto.clinica_nome_fantasia = "Nome fantasia";
        dto.clinica_razao_social = "Razão social";
        dto.clinica_cnpj = "11.243.612/0001-21";
        dto.clinica_cnae = "15378";
        dto.clinica_cep = "57490-970";
        dto.clinica_logradouro = "Rua Doutor Miguel Torres 19";
        dto.clinica_numero = "45";
        dto.clinica_bairro = "Centro";
        dto.clinica_cidade = "Água Branca";
        dto.clinica_estado = "AL";
        dto.clinica_celular = "(11) 91111-1111";
        dto.clinica_telefone = "(11) 1111-1111";
        dto.clinica_email = "clinica@teste.com";

        dto.dono_nome = "Camaeon";
        dto.dono_cpf = "920.137.300-71";
        dto.dono_rg = "13.764.333-0";
        dto.dono_cep = "57490-970";
        dto.dono_logradouro = "Rua Doutor Miguel Torres 19";
        dto.dono_numero = "45";
        dto.dono_bairro = "Centro";
        dto.dono_cidade = "Água Branca";
        dto.dono_estado = "AL";
        dto.dono_celular = "(11) 92222-1111";
        dto.dono_telefone = "(11) 2211-1111";
        dto.dono_email = "camaeon@teste.com";
        dto.dono_senha = "1234";

        dto.tecnico_nome = "Buior";
        dto.tecnico_cpf = "204.107.690-96";
        dto.tecnico_rg = "35.262.255-6";
        dto.tecnico_cep = "78734-228";
        dto.tecnico_logradouro = "Rua São Benedito";
        dto.tecnico_numero = "45";
        dto.tecnico_bairro = "Conjunto Habitacional Cidade de Deus";
        dto.tecnico_cidade = "Rondonópolis";
        dto.tecnico_estado = "MT";
        dto.tecnico_celular = "(11) 93334-1111";
        dto.tecnico_telefone = "(11) 22334-1211";
        dto.tecnico_email = "buior@teste.com";
        dto.tecnico_senha = "1234";
        dto.tecnico_crmv = "SP-1234";

        return dto;
    }

    public static ClinicaRequestDto factoryUpdatedClinicaDto() {
        ClinicaRequestDto dto = new ClinicaRequestDto();

        dto.nome_fantasia = "Nome fantasia 2";
        dto.razao_social = "Razão social 2";
        dto.cnpj = "11.243.612/0001-00";
        dto.cnae = "15";
        dto.cep = "01111-970";
        dto.logradouro = "Rua Doutor Miguel Torres";
        dto.numero = "12";
        dto.bairro = "Centro 2";
        dto.cidade = "São Paulo";
        dto.estado = "SP";
        dto.celular = "(11) 91222-1111";
        dto.telefone = "(11) 1112-1111";
        dto.email = "clinica2@teste.com";

        return dto;
    }
}

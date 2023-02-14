package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.services.ClinicaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
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
}

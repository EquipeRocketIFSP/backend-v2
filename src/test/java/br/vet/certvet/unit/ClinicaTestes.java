package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.services.ClinicaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ClinicaTestes {
    @Autowired
    private ClinicaService clinicaService;

    @Test
    public void criarClinica() {
        Clinica clinica = this.clinicaService.criar(ClinicaTestes.factoryClinicaInicialRequestDto());

        assertNotNull(clinica);
    }

    public static ClinicaInicialRequestDto factoryClinicaInicialRequestDto() {
        ClinicaInicialRequestDto dto = new ClinicaInicialRequestDto();

        dto.clinica_nome_fantasia = "Nome fantasia";
        dto.clinica_razao_social = "Razão social";
        dto.clinica_cnpj = "11.243.612/0001-21";
        dto.clinica_cnae = "15378";
        dto.clinica_cep = "03800-000";
        dto.clinica_logradouro = "Rua teste";
        dto.clinica_numero = "123";
        dto.clinica_bairro = "Bairro teste";
        dto.clinica_cidade = "São Paulo";
        dto.clinica_estado = "SP";
        dto.clinica_celular = "(11) 91111-1111";
        dto.clinica_telefone = "(11) 1111-1111";
        dto.clinica_email = "clinica@teste.com";

        dto.dono_nome = "Dono nome";
        dto.dono_cpf = "920.137.300-71";
        dto.dono_rg = "11.111.111-1";
        dto.dono_cep = "03808-100";
        dto.dono_logradouro = "Rua teste";
        dto.dono_numero = "456";
        dto.dono_bairro = "Bairro teste";
        dto.dono_cidade = "São Paulo";
        dto.dono_estado = "SP";
        dto.dono_celular = "(11) 91111-1111";
        dto.dono_telefone = "(11) 1111-1111";
        dto.dono_email = "dono@teste.com";
        dto.dono_senha = "1234";

        dto.tecnico_nome = "Técnico teste";
        dto.tecnico_cpf = "920.137.300-71";
        dto.tecnico_rg = "11.111.111-1";
        dto.tecnico_cep = "03808-100";
        dto.tecnico_logradouro = "Rua teste";
        dto.tecnico_numero = "123";
        dto.tecnico_bairro = "Bairro teste";
        dto.tecnico_cidade = "São Paulo";
        dto.tecnico_estado = "SP";
        dto.tecnico_celular = "(11) 91111-1111";
        dto.tecnico_telefone = "(11) 1111-1111";
        dto.tecnico_email = "tecnico@teste.com";
        dto.tecnico_senha = "1234";

        return dto;
    }

    public static Clinica factoryClinicaModel() {
        return new Clinica(ClinicaTestes.factoryClinicaInicialRequestDto());
    }
}

package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.ClinicaRequestDto;
import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.services.implementation.ClinicaServiceImpl;
import br.vet.certvet.services.implementation.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
public class ClinicaTest {
    private final ClinicaRepository clinicaRepository = mock(ClinicaRepository.class);

    private final UsuarioServiceImpl usuarioService = mock(UsuarioServiceImpl.class);

    @InjectMocks
    private ClinicaServiceImpl clinicaService;

    @Test
    public void createClinicaWithoutResponsavelTecnico() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();
        final Clinica expected = new Clinica(dto);

        when(this.clinicaRepository.saveAndFlush(any())).thenReturn(expected);

        final Clinica actual = this.clinicaService.create(dto);

        assertEquals(expected, actual);
    }

    @Test
    public void createClinicaWithResponsavelTecnico() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();
        dto.dono_crmv = "SP-1234";

        final Clinica expected = new Clinica(dto);
        final Usuario expectedDono = new Usuario(ClinicaTest.getDonoDto(dto), expected);

        expected.setResponsavelTecnico(expectedDono);

        when(this.clinicaRepository.saveAndFlush(any())).thenReturn(expected);
        when(this.usuarioService.create(any(FuncionarioRequestDto.class), any())).thenReturn(expectedDono);

        Clinica actual = this.clinicaService.create(dto);

        assertEquals(expected, actual);
    }

    @Test
    public void getExistentClinica() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();
        final Clinica expected = new Clinica(dto);

        when(this.clinicaRepository.findById(any())).thenReturn(Optional.of(expected));

        Clinica actual = this.clinicaService.findById(Long.parseLong("1"));

        assertEquals(expected, actual);
    }

    @Test
    public void getExistentClinicaByCnpj() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();
        final Clinica expected = new Clinica(dto);

        when(this.clinicaRepository.findByCnpj(any())).thenReturn(Optional.of(expected));

        final Clinica actual = this.clinicaService.findByCnpj("11.243.612/0001-21");

        assertEquals(expected, actual);
    }

    @Test
    public void getNotExistentClinica() {
        when(this.clinicaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrowsExactly(NotFoundException.class, () -> this.clinicaService.findById(Long.parseLong("999999999")));
    }

    @Test
    public void getNotExistentClinicaByCnpj() {
        when(this.clinicaRepository.findByCnpj(any())).thenReturn(Optional.empty());
        assertThrowsExactly(NotFoundException.class, () -> this.clinicaService.findByCnpj("11.222.333/0001-11"));
    }

    @Test
    public void editClinica() {
        final Clinica expected = new Clinica(ClinicaTest.factoryClinicaInicialRequestDto());
        expected.fill(ClinicaTest.factoryUpdatedClinicaDto());

        when(this.clinicaRepository.saveAndFlush(any())).thenReturn(expected);

        Clinica actual = new Clinica(ClinicaTest.factoryClinicaInicialRequestDto());

        assertNotEquals(expected, actual);

        actual = this.clinicaService.edit(ClinicaTest.factoryUpdatedClinicaDto(), actual);

        assertEquals(expected, actual);
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
        dto.dono_crmv = null;

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

    private static FuncionarioRequestDto getDonoDto(ClinicaInicialRequestDto dto) {
        FuncionarioRequestDto usuarioDto = new FuncionarioRequestDto();

        usuarioDto.nome = dto.dono_nome;
        usuarioDto.email = dto.dono_email;
        usuarioDto.cpf = dto.dono_cpf;
        usuarioDto.rg = dto.dono_rg;
        usuarioDto.celular = dto.dono_celular;
        usuarioDto.telefone = dto.dono_telefone;
        usuarioDto.logradouro = dto.dono_logradouro;
        usuarioDto.numero = dto.dono_numero;
        usuarioDto.cep = dto.dono_cep;
        usuarioDto.bairro = dto.dono_bairro;
        usuarioDto.cidade = dto.dono_cidade;
        usuarioDto.estado = dto.dono_estado;
        usuarioDto.senha = dto.dono_senha;
        usuarioDto.is_admin = true;

        return usuarioDto;
    }
}

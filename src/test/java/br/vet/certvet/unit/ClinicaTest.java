package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.ClinicaRequestDto;
import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.services.EmailService;
import br.vet.certvet.services.implementation.ClinicaServiceImpl;
import br.vet.certvet.services.implementation.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.MessagingException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
public class ClinicaTest {
    @Mock private ClinicaRepository clinicaRepository;// = mock(ClinicaRepository.class);

    @Mock private UsuarioServiceImpl usuarioService;// = mock(UsuarioServiceImpl.class);
    @Mock private EmailService emailService;

    @InjectMocks
    private ClinicaServiceImpl clinicaService;

    @Test
     void createClinicaWithoutResponsavelTecnico() {
        ClinicaInicialRequestDto dto = factoryClinicaInicialRequestDto();
        final Clinica expected = new Clinica(dto);

        when(clinicaRepository.findByCnpj(dto.clinicaCnpj())).thenReturn(Optional.empty());
        when(this.clinicaRepository.saveAndFlush(any())).thenReturn(expected);
        when(usuarioService.initialResgistration(any(), any())).thenReturn(new Usuario(ClinicaServiceImpl.getDonoDto(dto), expected));
        try {
            doNothing().when(emailService).sendTextMessage(any(), any(), any());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        final Clinica actual = this.clinicaService.create(dto);

        assertEquals(expected, actual);
    }

    @Test
     void createClinicaWithResponsavelTecnico() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();
        //dto.dono_crmv = "SP-1234";

        final Clinica expected = new Clinica(dto);
        final Usuario expectedDono = new Usuario(ClinicaTest.getDonoDto(dto), expected);

        expected.setResponsavelTecnico(expectedDono);

        when(usuarioService.initialResgistration(any(), any())).thenReturn(new Usuario(ClinicaServiceImpl.getDonoDto(dto), expected));
        when(this.clinicaRepository.saveAndFlush(any())).thenReturn(expected);
        when(this.usuarioService.create(any(FuncionarioRequestDto.class), any())).thenReturn(expectedDono);
        try {
            doNothing().when(emailService).sendTextMessage(any(), any(), any());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        Clinica actual = this.clinicaService.create(dto);

        assertEquals(expected, actual);
    }

    @Test
     void getExistentClinica() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();
        final Clinica expected = new Clinica(dto);

        when(this.clinicaRepository.findById(any())).thenReturn(Optional.of(expected));

        Clinica actual = this.clinicaService.findById(Long.parseLong("1"));

        assertEquals(expected, actual);
    }

    @Test
     void getExistentClinicaByCnpj() {
        ClinicaInicialRequestDto dto = ClinicaTest.factoryClinicaInicialRequestDto();
        final Clinica expected = new Clinica(dto);

        when(this.clinicaRepository.findByCnpj(any())).thenReturn(Optional.of(expected));

        final Clinica actual = this.clinicaService.findByCnpj("11.243.612/0001-21");

        assertEquals(expected, actual);
    }

    @Test
     void getNotExistentClinica() {
        when(this.clinicaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrowsExactly(NotFoundException.class, () -> this.clinicaService.findById(Long.parseLong("999999999")));
    }

    @Test
     void getNotExistentClinicaByCnpj() {
        when(this.clinicaRepository.findByCnpj(any())).thenReturn(Optional.empty());
        assertThrowsExactly(NotFoundException.class, () -> this.clinicaService.findByCnpj("11.222.333/0001-11"));
    }

    @Test
     void editClinica() {
        final Clinica expected = new Clinica(ClinicaTest.factoryClinicaInicialRequestDto());
        expected.fill(ClinicaTest.factoryUpdatedClinicaDto());

        when(this.clinicaRepository.saveAndFlush(any())).thenReturn(expected);

        Clinica actual = new Clinica(ClinicaTest.factoryClinicaInicialRequestDto());

        assertNotEquals(expected, actual);

        actual = this.clinicaService.edit(ClinicaTest.factoryUpdatedClinicaDto(), actual);

        assertEquals(expected, actual);
    }

    private static ClinicaInicialRequestDto factoryClinicaInicialRequestDto() {
        return new ClinicaInicialRequestDto(
                "Nome fantasia",
                "Razão social",
                "11.243.612/0001-21",
                "15378",
                "57490-970",
                "Rua Doutor Miguel Torres 19",
                "45",
                "Centro",
                "Água Branca",
                "AL",
                "(11) 91111-1111",
                "(11) 1111-1111",
                "clinica@teste.com",

                "Camaeon",
                "920.137.300-71",
                "13.764.333-0",
                "57490-970",
                "Rua Doutor Miguel Torres 19",
                "45",
                "Centro",
                "Água Branca",
                "AL",
                "(11) 92222-1111",
                "(11) 2211-1111",
                "camaeon@teste.com",
                "1234",
                null
        );
    }

    public static ClinicaRequestDto factoryUpdatedClinicaDto() {
        return new ClinicaRequestDto(
                "Nome fantasia 2",
                "Razão social 2",
                "11.243.612/0001-00",
                "15",
                "01111-970",
                "Rua Doutor Miguel Torres",
                "12",
                "Centro 2",
                "São Paulo",
                "SP",
                "(11) 91222-1111",
                "(11) 1112-1111",
                "clinica2@teste.com"
        );
    }

    private static FuncionarioRequestDto getDonoDto(ClinicaInicialRequestDto dto) {
        FuncionarioRequestDto usuarioDto = new FuncionarioRequestDto();

        usuarioDto.setNome(dto.donoNome());
        usuarioDto.setEmail(dto.donoEmail());
        usuarioDto.setCpf(dto.donoCpf());
        usuarioDto.setRg(dto.donoRg());
        usuarioDto.setCelular(dto.donoCelular());
        usuarioDto.setTelefone(dto.donoTelefone());
        usuarioDto.setLogradouro(dto.donoLogradouro());
        usuarioDto.setNumero(dto.donoNumero());
        usuarioDto.setCep(dto.donoCep());
        usuarioDto.setBairro(dto.donoBairro());
        usuarioDto.setCidade(dto.donoCidade());
        usuarioDto.setEstado(dto.donoEstado());
        usuarioDto.setSenha(dto.donoSenha());
        usuarioDto.setAdmin(true);

        return usuarioDto;
    }
}

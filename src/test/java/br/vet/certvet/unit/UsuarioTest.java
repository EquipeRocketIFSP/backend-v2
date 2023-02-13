package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.UsuarioRepository;
import br.vet.certvet.services.implementation.ClinicaServiceImpl;
import br.vet.certvet.services.implementation.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private ClinicaServiceImpl clinicaService;

    private static Clinica clinica;

    @BeforeAll
    public void setup() {
        UsuarioTest.clinica = this.clinicaService.create(ClinicaTestes.factoryClinicaInicialRequestDto());
    }

    @AfterEach
    public void truncateTable() {
        this.usuarioRepository.deleteAll();
    }

    @Test
    public void createDono() {
        final String[] AUTHORITIES = {"FUNCIONARIO", "ADMIN"};

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryDonoRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    public void createTutor() {
        final String[] AUTHORITIES = {"TUTOR"};

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryTutorRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    public void createVeterinario() {
        final String[] AUTHORITIES = {"VETERINARIO"};

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryVeterinarioRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    public void createFuncionario() {
        final String[] AUTHORITIES = {"FUNCIONARIO"};

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryFuncionarioRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    public void recuperarTutorExistente() {
        Usuario usuarioTest = this.usuarioService.create(UsuarioTest.factoryTutorRequestDto(), UsuarioTest.clinica);
        Usuario usuario = this.usuarioService.findOne(usuarioTest.getId(), UsuarioTest.clinica);

        assertNotNull(usuario);
        assertEquals(usuario, usuarioTest);
    }

    @Test
    public void recuperarTutorInexistente() {
        assertThrowsExactly(NotFoundException.class, () -> {
            this.usuarioService.findOne(Long.getLong("999999999"), UsuarioTest.clinica);
        });
    }

    public static UsuarioRequestDto factoryTutorRequestDto() {
        UsuarioRequestDto dto = new UsuarioRequestDto();

        dto.nome = "Dorguk";
        dto.cpf = "920.137.300-71";
        dto.rg = "36.264.815-3";
        dto.cep = "04258-100";
        dto.logradouro = "Rua Ikuyo Tamura";
        dto.numero = "15";
        dto.bairro = "Vila Arapuã";
        dto.cidade = "São Paulo";
        dto.estado = "SP";
        dto.celular = "(11) 91111-1111";
        dto.telefone = "(11) 1111-1111";
        dto.email = "dorguk@teste.com";

        return dto;
    }

    public static FuncionarioRequestDto factoryDonoRequestDto() {
        FuncionarioRequestDto dto = new FuncionarioRequestDto();

        dto.nome = "Camaeon";
        dto.cpf = "920.137.300-71";
        dto.rg = "13.764.333-0";
        dto.cep = "57490-970";
        dto.logradouro = "Rua Doutor Miguel Torres 19";
        dto.numero = "45";
        dto.bairro = "Centro";
        dto.cidade = "Água Branca";
        dto.estado = "AL";
        dto.celular = "(11) 92222-1111";
        dto.telefone = "(11) 2211-1111";
        dto.email = "camaeon@teste.com";
        dto.senha = "1234";
        dto.is_admin = true;

        return dto;
    }

    public static FuncionarioRequestDto factoryFuncionarioRequestDto() {
        FuncionarioRequestDto dto = new FuncionarioRequestDto();

        dto.nome = "Liero";
        dto.cpf = "413.301.570-36";
        dto.rg = "20.272.584-4";
        dto.cep = "68908-641";
        dto.logradouro = "Avenida Brasil";
        dto.numero = "45";
        dto.bairro = "Boné Azul";
        dto.cidade = "Macapá";
        dto.estado = "AP";
        dto.celular = "(11) 93333-1111";
        dto.telefone = "(11) 22333-1111";
        dto.email = "liero@teste.com";
        dto.senha = "1234";
        dto.is_admin = false;

        return dto;
    }

    public static VeterinarioRequestDto factoryVeterinarioRequestDto() {
        VeterinarioRequestDto dto = new VeterinarioRequestDto();

        dto.nome = "Buior";
        dto.cpf = "204.107.690-96";
        dto.rg = "35.262.255-6";
        dto.cep = "78734-228";
        dto.logradouro = "Rua São Benedito";
        dto.numero = "45";
        dto.bairro = "Conjunto Habitacional Cidade de Deus";
        dto.cidade = "Rondonópolis";
        dto.estado = "MT";
        dto.celular = "(11) 93334-1111";
        dto.telefone = "(11) 22334-1211";
        dto.email = "buior@teste.com";
        dto.senha = "1234";
        dto.is_admin = false;
        dto.crmv = "SP-1234";

        return dto;
    }
}

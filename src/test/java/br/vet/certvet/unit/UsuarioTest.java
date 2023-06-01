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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableConfigurationProperties
public class UsuarioTest {
    private UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);

    private UsuarioServiceImpl usuarioService = mock(UsuarioServiceImpl.class);

    private ClinicaServiceImpl clinicaService = mock(ClinicaServiceImpl.class);

    private static Clinica clinica;
    private static BCryptPasswordEncoder passwordEncoder;

    @BeforeAll
    void setup() {
        UsuarioTest.clinica = this.clinicaService.create(ClinicaTest.factoryClinicaInicialRequestDto());
        UsuarioTest.passwordEncoder = new BCryptPasswordEncoder();
    }

    @AfterEach
    void truncateTable() {
        this.usuarioRepository.deleteAll();
    }

    @Test
    void createDono() {
        final String[] AUTHORITIES = {"FUNCIONARIO", "ADMIN"};

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryDonoRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    void createTutor() {
        final String[] AUTHORITIES = {"TUTOR"};

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryTutorRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    void createVeterinario() {
        final String[] AUTHORITIES = {"VETERINARIO"};

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryVeterinarioRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    void createFuncionario() {
        final String[] AUTHORITIES = {"FUNCIONARIO"};

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryFuncionarioRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    void editDono() {
        final String[] AUTHORITIES = {"FUNCIONARIO", "ADMIN"};

        FuncionarioRequestDto dto = UsuarioTest.factoryDonoRequestDto();
        Usuario usuario = this.usuarioService.create(dto, UsuarioTest.clinica);
        Long idUsuario = usuario.getId();

        UsuarioTest.updateUsuarioDto(dto);
        dto.setSenha("4321");

        final Usuario USUARIO_COMPARATION = new Usuario(dto, UsuarioTest.clinica);
        usuario = this.usuarioService.edit(dto, usuario);

        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertEquals(usuario.getId(), idUsuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
        assertTrue(UsuarioTest.passwordEncoder.matches(dto.getSenha(), usuario.getPassword()));

        assertThat(usuario.getClinica().getId())
                .isEqualTo(UsuarioTest.clinica.getId());

        assertThat(usuario)
                .usingRecursiveComparison()
                .ignoringFields("id", "clinica", "password", "authorities")
                .isEqualTo(USUARIO_COMPARATION);
    }

    @Test
    void editTutor() {
        final String[] AUTHORITIES = {"TUTOR"};

        UsuarioRequestDto dto = UsuarioTest.factoryTutorRequestDto();
        Usuario usuario = this.usuarioService.create(dto, UsuarioTest.clinica);
        Long idUsuario = usuario.getId();

        UsuarioTest.updateUsuarioDto(dto);

        final Usuario USUARIO_COMPARATION = new Usuario(dto, UsuarioTest.clinica);
        usuario = this.usuarioService.edit(dto, usuario);

        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertEquals(usuario.getId(), idUsuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);

        assertThat(usuario.getClinica().getId())
                .isEqualTo(UsuarioTest.clinica.getId());

        assertThat(usuario)
                .usingRecursiveComparison()
                .ignoringFields("id", "clinica", "password", "authorities")
                .isEqualTo(USUARIO_COMPARATION);
    }

    @Test
    void editVeterinario() {
        final String[] AUTHORITIES = {"VETERINARIO"};

        VeterinarioRequestDto dto = UsuarioTest.factoryVeterinarioRequestDto();
        Usuario usuario = this.usuarioService.create(dto, UsuarioTest.clinica);
        Long idUsuario = usuario.getId();

        UsuarioTest.updateUsuarioDto(dto);
        dto.setSenha("4321");
        dto.setCrmv("RJ-12345");

        final Usuario USUARIO_COMPARATION = new Usuario(dto, UsuarioTest.clinica);
        usuario = this.usuarioService.edit(dto, usuario);

        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertEquals(usuario.getId(), idUsuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
        assertTrue(UsuarioTest.passwordEncoder.matches(dto.getSenha(), usuario.getPassword()));

        assertThat(usuario.getClinica().getId())
                .isEqualTo(UsuarioTest.clinica.getId());

        assertThat(usuario)
                .usingRecursiveComparison()
                .ignoringFields("id", "clinica", "password", "authorities")
                .isEqualTo(USUARIO_COMPARATION);
    }

    @Test
    void editFuncionario() {
        final String[] AUTHORITIES = {"FUNCIONARIO"};

        FuncionarioRequestDto dto = UsuarioTest.factoryFuncionarioRequestDto();
        Usuario usuario = this.usuarioService.create(dto, UsuarioTest.clinica);
        Long idUsuario = usuario.getId();

        UsuarioTest.updateUsuarioDto(dto);
        dto.setSenha("4321");

        final Usuario USUARIO_COMPARATION = new Usuario(dto, UsuarioTest.clinica);
        usuario = this.usuarioService.edit(dto, usuario);

        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        assertNotNull(usuario);
        assertEquals(usuario.getId(), idUsuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
        assertTrue(UsuarioTest.passwordEncoder.matches(dto.getSenha(), usuario.getPassword()));

        assertThat(usuario.getClinica().getId())
                .isEqualTo(UsuarioTest.clinica.getId());

        assertThat(usuario)
                .usingRecursiveComparison()
                .ignoringFields("id", "clinica", "password", "authorities")
                .isEqualTo(USUARIO_COMPARATION);
    }

    @Test
    void getExistentTutor() {
        Usuario usuarioTest = this.usuarioService.create(UsuarioTest.factoryTutorRequestDto(), UsuarioTest.clinica);
        Usuario usuario = this.usuarioService.findOne(usuarioTest.getId(), UsuarioTest.clinica);

        assertNotNull(usuario);
        assertEquals(usuario, usuarioTest);
    }

    @Test
    void getUnexistentTutor() {
        assertThrowsExactly(NotFoundException.class, () -> {
            this.usuarioService.findOne(999999999L, UsuarioTest.clinica);
        });
    }

    @Test
    void softDeleteUsuario() {
        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryVeterinarioRequestDto(), UsuarioTest.clinica);
        this.usuarioService.delete(usuario);

        assertThat(usuario.getDeletedAt()).isNotNull();
    }

    @Test
    void recoverDeleteUsuario() {
        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryTutorRequestDto(), UsuarioTest.clinica);
        this.usuarioService.delete(usuario);
        this.usuarioService.recover(usuario);

        assertThat(usuario.getDeletedAt()).isNull();
    }

    @Test
    void findTutorAuthority() {
        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryTutorRequestDto(), UsuarioTest.clinica);

        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "TUTOR")).isPresent();

        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "ADMIN")).isEmpty();
        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "VETERINARIO")).isEmpty();
        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "FUNCIONARIO")).isEmpty();
    }

    @Test
    void findFuncionarioAuthority() {
        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryFuncionarioRequestDto(), UsuarioTest.clinica);

        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "FUNCIONARIO")).isPresent();

        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "ADMIN")).isEmpty();
        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "VETERINARIO")).isEmpty();
        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "TUTOR")).isEmpty();
    }

    @Test
    void findVeterinarioAuthority() {
        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryVeterinarioRequestDto(), UsuarioTest.clinica);

        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "VETERINARIO")).isPresent();

        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "ADMIN")).isEmpty();
        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "TUTOR")).isEmpty();
        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "FUNCIONARIO")).isEmpty();
    }

    @Test
    void findDonoAuthority() {
        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryDonoRequestDto(), UsuarioTest.clinica);

        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "ADMIN")).isPresent();
        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "FUNCIONARIO")).isPresent();

        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "TUTOR")).isEmpty();
        assertThat(this.usuarioService.findUsuarioAuthority(usuario, "VETERINARIO")).isEmpty();
    }

    private static UsuarioRequestDto factoryTutorRequestDto() {
        UsuarioRequestDto dto = new UsuarioRequestDto();

        dto.setNome("Dorguk");
        dto.setCpf("920.137.300-71");
        dto.setRg("36.264.815-3");
        dto.setCep("04258-100");
        dto.setLogradouro("Rua Ikuyo Tamura");
        dto.setNumero("15");
        dto.setBairro("Vila Arapuã");
        dto.setCidade("São Paulo");
        dto.setEstado("SP");
        dto.setCelular("(11) 91111-1111");
        dto.setTelefone("(11) 1111-1111");
        dto.setEmail("dorguk@teste.com");

        return dto;
    }

    private static FuncionarioRequestDto factoryDonoRequestDto() {
        FuncionarioRequestDto dto = new FuncionarioRequestDto();

        dto.setNome("Camaeon");
        dto.setCpf("920.137.300-71");
        dto.setRg("13.764.333-0");
        dto.setCep("57490-970");
        dto.setLogradouro("Rua Doutor Miguel Torres 19");
        dto.setNumero("45");
        dto.setBairro("Centro");
        dto.setCidade("Água Branca");
        dto.setEstado("AL");
        dto.setCelular("(11) 92222-1111");
        dto.setTelefone("(11) 2211-1111");
        dto.setEmail("camaeon@teste.com");
        dto.setSenha("1234");
        dto.setAdmin(true);

        return dto;
    }

    private static FuncionarioRequestDto factoryFuncionarioRequestDto() {
        FuncionarioRequestDto dto = new FuncionarioRequestDto();

        dto.setNome("Liero");
        dto.setCpf("413.301.570-36");
        dto.setRg("20.272.584-4");
        dto.setCep("68908-641");
        dto.setLogradouro("Avenida Brasil");
        dto.setNumero("45");
        dto.setBairro("Boné Azul");
        dto.setCidade("Macapá");
        dto.setEstado("AP");
        dto.setCelular("(11) 93333-1111");
        dto.setTelefone("(11) 22333-1111");
        dto.setEmail("liero@teste.com");
        dto.setSenha("1234");
        dto.setAdmin(false);

        return dto;
    }

    private static VeterinarioRequestDto factoryVeterinarioRequestDto() {
        VeterinarioRequestDto dto = new VeterinarioRequestDto();

        dto.setNome("Buior");
        dto.setCpf("204.107.690-96");
        dto.setRg("35.262.255-6");
        dto.setCep("78734-228");
        dto.setLogradouro("Rua São Benedito");
        dto.setNumero("45");
        dto.setBairro("Conjunto Habitacional Cidade de Deus");
        dto.setCidade("Rondonópolis");
        dto.setEstado("MT");
        dto.setCelular("(11) 93334-1111");
        dto.setTelefone("(11) 22334-1211");
        dto.setEmail("buior@teste.com");
        dto.setSenha("1234");
        dto.setAdmin(false);
        dto.setCrmv("SP-1234");

        return dto;
    }

    private static void updateUsuarioDto(UsuarioRequestDto dto) {
        dto.setNome("Nome Teste");
        dto.setCpf("teste@teste.com");
        dto.setRg("11111-000");
        dto.setCep("Bairro Teste");
        dto.setLogradouro("Logradouro Teste");
        dto.setNumero("Cidade Teste");
        dto.setBairro("SP");
        dto.setCidade("111.111.111-11");
        dto.setEstado("11.111.111-1");
        dto.setCelular("(11) 90000-1111");
        dto.setTelefone("(11) 0000-1111");
    }
}

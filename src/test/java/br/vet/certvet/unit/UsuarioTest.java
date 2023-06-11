package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.AuthorityRepository;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.UsuarioRepository;
import br.vet.certvet.services.EmailService;
import br.vet.certvet.services.implementation.UsuarioServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
class UsuarioTest {
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private ClinicaRepository clinicaRepository;
    @Mock private AuthorityRepository authorityRepository;

    @Mock private EmailService emailService;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    private static Clinica clinica;

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

    private Clinica newClinica(){
        return Clinica.builder()
                .celular("(11) 91111-1111")
                .email("clinica@teste.com")
                .id(1L)
                .cep("57490-970")
                .cidade("Água Branca")
                .razaoSocial("Razão social")
                .nomeFantasia("Nome fantasia")
                .cnpj("11.243.612/0001-21")
                .cnae("15378")
                .code("logico12")
                .estado("AL")
                .bairro("Centro")
                .build();
    }
    private Usuario newUsuario(String[] auths){
        return Usuario.builder()
                .id(null)
                .username("camaeon@teste.com")
                .password("$2a$10$B1JCg6ULYTAj2dikHO3jWON0oWNkxQ8D5O/ryn7jj9cODipZj/qP2")
                .nome("Camaeon")
                .cpf("920.137.300-71")
                .rg("13.764.333-0")
                .cep("057490-970")
                .logradouro("Rua Doutor Miguel Torres 19")
                .numero("45")
                .bairro("Centro")
                .cidade("Água Branca")
                .estado("AL")
                .celular("(11) 91111-1111")
                .telefone("(11) 2211-1111")
                .crmv(null)
                .deletedAt(null)
                .resetPasswordToken(null)
                .email(null)
                .authorities(
                        Arrays.stream(auths)
                                .map(auth -> Authority.builder()
                                        .permissao(auth)
                                        .build())
                                .toList()
                )
                .build();
    }

    @BeforeEach
    public void setUp() {
        clinica = newClinica();
    }

    @AfterEach
    void tearDown() {
        clinica = null;
    }

    @Test
    void whenDonoWithoutActiveVet_thenCreateNewUser() {
        final String[] AUTHORITIES = {"FUNCIONARIO", "ADMIN"};
        final Usuario parametro = newUsuario(AUTHORITIES);
        try {
            doNothing().when(emailService).sendTextMessage(any(),any(),any());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        when(authorityRepository.findByPermissao("FUNCIONARIO")).thenReturn(Authority.builder().id(3L).permissao("FUNCIONARIO").build());
        when(authorityRepository.findByPermissao("VETERINARIO")).thenReturn(Authority.builder().id(2L).permissao("VETERINARIO").build());
        when(usuarioRepository.findByUsernameAndClinica(any(), any())).thenReturn(Optional.empty());
        when(usuarioRepository.saveAndFlush(any())).thenReturn(parametro);

        final Usuario usuario = usuarioService.create(factoryDonoRequestDto(), clinica);
        final List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getPermissao).toList();

        assertEquals(parametro, usuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    void whenNewTutorIsRequested_thenCreateNewUser() {
        final String[] AUTHORITIES = {"TUTOR"};
        final Usuario parametro = newUsuario(AUTHORITIES);
        try {
            doNothing().when(emailService).sendTextMessage(any(),any(),any());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        when(authorityRepository.findByPermissao("TUTOR")).thenReturn(Authority.builder().permissao("TUTOR").build());
        when(usuarioRepository.findByUsernameAndClinica(any(), any())).thenReturn(Optional.empty());
        when(usuarioRepository.saveAndFlush(any())).thenReturn(parametro);

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryTutorRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getPermissao).toList();

        assertEquals(parametro, usuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    void whenNewVeterinarioIsRequested_thenCreateNewUser() {
        final String[] AUTHORITIES = {"VETERINARIO"};
        final Usuario parametro = newUsuario(AUTHORITIES);
        try {
            doNothing().when(emailService).sendTextMessage(any(),any(),any());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        when(authorityRepository.findByPermissao("VETERINARIO")).thenReturn(Authority.builder().permissao("VETERINARIO").build());
        when(usuarioRepository.findByUsernameAndClinica(any(), any())).thenReturn(Optional.empty());
        when(usuarioRepository.saveAndFlush(any())).thenReturn(parametro);

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryVeterinarioRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getPermissao).toList();

        assertEquals(parametro, usuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    void whenNewFuncionarioIsRequested_thenCreateNewUser() {
        final String[] AUTHORITIES = {"FUNCIONARIO"};
        final Usuario parametro = newUsuario(AUTHORITIES);
        try {
            doNothing().when(emailService).sendTextMessage(any(),any(),any());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        when(authorityRepository.findByPermissao("FUNCIONARIO")).thenReturn(Authority.builder().permissao("FUNCIONARIO").build());
        when(usuarioRepository.findByUsernameAndClinica(any(), any())).thenReturn(Optional.empty());
        when(usuarioRepository.saveAndFlush(any())).thenReturn(parametro);

        Usuario usuario = this.usuarioService.create(UsuarioTest.factoryFuncionarioRequestDto(), UsuarioTest.clinica);
        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getPermissao).toList();

        assertEquals(parametro, usuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
    }

    @Test
    void editDono() {
        final String[] AUTHORITIES = {"FUNCIONARIO", "ADMIN"};

        FuncionarioRequestDto dto = UsuarioTest.factoryDonoRequestDto();
        Usuario usuario = this.usuarioService.create(dto, UsuarioTest.clinica);
        Long idUsuario = usuario.getId();

        updateUsuarioDto(dto);
        dto.setSenha("4321");

        final Usuario USUARIO_COMPARATION = new Usuario(dto, UsuarioTest.clinica);
        usuario = this.usuarioService.edit(dto, usuario);

        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getPermissao).toList();

        assertNotNull(usuario);
        assertEquals(usuario.getId(), idUsuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
        //assertTrue(UsuarioTest.passwordEncoder.matches(dto.getSenha(), usuario.getPassword()));

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

        when(usuarioRepository.saveAndFlush(any())).thenReturn(Usuario.builder().build());

        UsuarioRequestDto dto = UsuarioTest.factoryTutorRequestDto();
        Usuario usuario = this.usuarioService.create(dto, UsuarioTest.clinica);
        Long idUsuario = usuario.getId();

        updateUsuarioDto(dto);

        final Usuario USUARIO_COMPARATION = new Usuario(dto, UsuarioTest.clinica);
        usuario = this.usuarioService.edit(dto, usuario);

        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getPermissao).toList();

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

        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getPermissao).toList();

        assertNotNull(usuario);
        assertEquals(usuario.getId(), idUsuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
        //assertTrue(UsuarioTest.passwordEncoder.matches(dto.getSenha(), usuario.getPassword()));

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

        List<String> usuarioAuthorities = usuario.getAuthorities().stream().map(Authority::getPermissao).toList();

        assertNotNull(usuario);
        assertEquals(usuario.getId(), idUsuario);
        assertEquals(Arrays.stream(AUTHORITIES).toList(), usuarioAuthorities);
       // assertTrue(UsuarioTest.passwordEncoder.matches(dto.getSenha(), usuario.getPassword()));

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
        return (FuncionarioRequestDto) new FuncionarioRequestDto()
                .setSenha("1234")
                .setAdmin(true)
                .setNome("Camaeon")
                .setCpf("920.137.300-71")
                .setRg("13.764.333-0")
                .setCep("57490-970")
                .setLogradouro("Rua Doutor Miguel Torres 19")
                .setNumero("45")
                .setBairro("Centro")
                .setCidade("Água Branca")
                .setEstado("AL")
                .setCelular("(11) 92222-1111")
                .setTelefone("(11) 2211-1111")
                .setEmail("camaeon@teste.com");
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

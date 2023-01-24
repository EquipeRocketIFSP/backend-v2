package br.vet.certvet.unit;

import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

//@TODO: corrigir teste pós atualização das validações.
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioTestes {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClinicaService clinicaService;

    private static Clinica clinica;

    @BeforeAll
    public void criarClinica() {
        UsuarioTestes.clinica = this.clinicaService.criar(ClinicaTestes.factoryClinicaInicialRequestDto());
    }

    @Test
    public void criarUsuario() {
        Usuario usuario = this.usuarioService.criar(UsuarioTestes.factoryUsuarioAtivoRequestDto(), UsuarioTestes.clinica);

        assertNotNull(usuario);
    }

    @Test
    public void criarTutor() {
        Usuario usuario = this.usuarioService.criar(UsuarioTestes.factoryUsuarioRequestDto(), UsuarioTestes.clinica);

        assertNotNull(usuario);
    }

    @Test
    public void recuperarTutorExistente() {
        Usuario usuarioTest = this.usuarioService.criar(UsuarioTestes.factoryUsuarioRequestDto(), UsuarioTestes.clinica);
        Usuario usuario = this.usuarioService.findById(usuarioTest.getId());

        assertNotNull(usuario);
        assertEquals(usuario, usuarioTest);
    }

    @Test
    public void recuperarTutorInexistente() {
        assertThrowsExactly(NotFoundException.class, () -> {
            this.usuarioService.findById(Long.getLong("999999999"));
        });
    }

    public static UsuarioRequestDto factoryUsuarioRequestDto() {
        UsuarioRequestDto dto = new UsuarioRequestDto();

        dto.nome = "Teste nome";
        dto.cpf = "920.137.300-71";
        dto.rg = "11.111.111-1";
        dto.cep = "03808-100";
        dto.logradouro = "Rua teste";
        dto.numero = "456";
        dto.bairro = "Bairro teste";
        dto.cidade = "São Paulo";
        dto.estado = "SP";
        dto.celular = "(11) 91111-1111";
        dto.telefone = "(11) 1111-1111";
        dto.email = "teste@teste.com";

        return dto;
    }

    public static FuncionarioRequestDto factoryUsuarioAtivoRequestDto() {
        FuncionarioRequestDto dto = new FuncionarioRequestDto();

        dto.nome = "Dono nome";
        dto.cpf = "920.137.300-71";
        dto.rg = "11.111.111-1";
        dto.cep = "03808-100";
        dto.logradouro = "Rua teste";
        dto.numero = "456";
        dto.bairro = "Bairro teste";
        dto.cidade = "São Paulo";
        dto.estado = "SP";
        dto.celular = "(11) 91111-1111";
        dto.telefone = "(11) 1111-1111";
        dto.email = "dono@teste.com";
        dto.senha = "1234";

        return dto;
    }
}

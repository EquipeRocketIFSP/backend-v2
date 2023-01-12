package br.vet.certvet.unit;

import br.vet.certvet.dto.request.UsuarioAtivoRequestDto;
import br.vet.certvet.dto.request.UsuarioRequestDto;
import br.vet.certvet.model.Usuario;
import br.vet.certvet.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioTestes {
    @Autowired
    private UsuarioService usuarioService;

    @Test
    public void criarUsuario() {
        Usuario usuario = this.usuarioService.criar(UsuarioTestes.factoryUsuarioAtivoRequestDto());

        assertNotNull(usuario);
    }

    @Test
    public void criarTutor() {
        Usuario usuario = this.usuarioService.criar(UsuarioTestes.factoryUsuarioRequestDto());

        assertNotNull(usuario);
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

    public static UsuarioAtivoRequestDto factoryUsuarioAtivoRequestDto() {
        UsuarioAtivoRequestDto dto = new UsuarioAtivoRequestDto();

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

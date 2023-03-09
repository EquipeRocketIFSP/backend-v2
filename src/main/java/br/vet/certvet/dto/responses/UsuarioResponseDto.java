package br.vet.certvet.dto.responses;

import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.models.Usuario;

public class UsuarioResponseDto extends UsuarioRequestDto {
    public Long id;

    public UsuarioResponseDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getUsername();
        this.cpf = usuario.getCpf();
        this.rg = usuario.getRg();
        this.cep = usuario.getCep();
        this.logradouro = usuario.getLogradouro();
        this.numero = usuario.getNumero();
        this.bairro = usuario.getBairro();
        this.cidade = usuario.getCidade();
        this.estado = usuario.getEstado();
        this.celular = usuario.getCelular();
        this.telefone = usuario.getTelefone();
    }
}

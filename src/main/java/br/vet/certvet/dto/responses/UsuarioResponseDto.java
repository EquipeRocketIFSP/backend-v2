package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioResponseDto {
    @JsonProperty("id")
    public Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("rg")
    private String rg;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("numero")
    private String numero;

    @JsonProperty("bairro")
    private String bairro;

    @JsonProperty("cidade")
    private String cidade;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("celular")
    private String celular;

    @JsonProperty("telefone")
    private String telefone;

    @JsonProperty("email")
    private String email;

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

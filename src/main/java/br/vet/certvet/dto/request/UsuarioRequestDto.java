package br.vet.certvet.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UsuarioRequestDto {
    @NotEmpty(message = "Nome não pode estar vazio")
    @Size(max = 255, message = "Nome não pode ultrapassar 255 caracteres")
    public String nome;

    @NotEmpty(message = "CPF não pode estar vazio")
    @Size(max = 14, message = "CPF não pode ultrapassar 14 caracteres")
    public String cpf;

    @NotEmpty(message = "RG não pode estar vazio")
    @Size(max = 255, message = "RG não pode ultrapassar 255 caracteres")
    public String rg;

    @NotEmpty(message = "CEP não pode estar vazio")
    @Size(max = 9, message = "CEP não pode ultrapassar 9 caracteres")
    public String cep;

    @NotEmpty(message = "Logradouro não pode estar vazio")
    @Size(max = 255, message = "Nome não pode ultrapassar 255 caracteres")
    public String logradouro;

    @NotEmpty(message = "Número não pode estar vazio")
    @Size(max = 6, message = "Número não pode ultrapassar 6 caracteres")
    public String numero;

    @NotEmpty(message = "Bairro não pode estar vazio")
    @Size(max = 255, message = "Bairro não pode ultrapassar 255 caracteres")
    public String bairro;

    @NotEmpty(message = "Cidade não pode estar vazio")
    @Size(max = 255, message = "Cidade não pode ultrapassar 255 caracteres")
    public String cidade;

    @NotEmpty(message = "Estado não pode estar vazio")
    @Size(max = 2, message = "Estado não pode ultrapassar 2 caracteres")
    public String estado;

    @NotEmpty(message = "Celular não pode estar vazio")
    @Size(max = 15, message = "Celular não pode ultrapassar 15 caracteres")
    public String celular;

    @Size(max = 14, message = "Telefone não pode ultrapassar 14 caracteres")
    public String telefone;

    @NotEmpty(message = "E-mail não pode estar vazio")
    @Size(max = 255, message = "E-mail não pode ultrapassar 255 caracteres")
    public String email;
}

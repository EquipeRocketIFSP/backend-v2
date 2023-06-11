package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Accessors(chain = true)
public class UsuarioRequestDto {
    @JsonProperty("nome")
    @NotEmpty(message = "Nome não pode estar vazio")
    @Size(max = 255, message = "Nome não pode ultrapassar 255 caracteres")
    protected String nome;

    @JsonProperty("cpf")
    @NotEmpty(message = "CPF não pode estar vazio")
    @Size(max = 14, message = "CPF não pode ultrapassar 14 caracteres")
    @CPF(message = "Insira um CPF válido")
    protected String cpf;

    @JsonProperty("rg")
    @NotEmpty(message = "RG não pode estar vazio")
    @Size(max = 255, message = "RG não pode ultrapassar 255 caracteres")
    protected String rg;

    @JsonProperty("cep")
    @NotEmpty(message = "CEP não pode estar vazio")
    @Size(max = 9, message = "CEP não pode ultrapassar 9 caracteres")
    protected String cep;

    @JsonProperty("logradouro")
    @NotEmpty(message = "Logradouro não pode estar vazio")
    @Size(max = 255, message = "Nome não pode ultrapassar 255 caracteres")
    protected String logradouro;

    @JsonProperty("numero")
    @NotEmpty(message = "Número não pode estar vazio")
    @Size(max = 6, message = "Número não pode ultrapassar 6 caracteres")
    protected String numero;

    @JsonProperty("bairro")
    @NotEmpty(message = "Bairro não pode estar vazio")
    @Size(max = 255, message = "Bairro não pode ultrapassar 255 caracteres")
    protected String bairro;

    @JsonProperty("cidade")
    @NotEmpty(message = "Cidade não pode estar vazio")
    @Size(max = 255, message = "Cidade não pode ultrapassar 255 caracteres")
    protected String cidade;

    @JsonProperty("estado")
    @NotEmpty(message = "Estado não pode estar vazio")
    @Size(max = 2, message = "Estado não pode ultrapassar 2 caracteres")
    protected String estado;

    @JsonProperty("celular")
    @NotEmpty(message = "Celular não pode estar vazio")
    @Size(max = 15, message = "Celular não pode ultrapassar 15 caracteres")
    protected String celular;

    @JsonProperty("telefone")
    @Size(max = 14, message = "Telefone não pode ultrapassar 14 caracteres")
    protected String telefone;

    @JsonProperty("email")
    @NotEmpty(message = "E-mail não pode estar vazio")
    @Size(max = 255, message = "E-mail não pode ultrapassar 255 caracteres")
    protected String email;
}

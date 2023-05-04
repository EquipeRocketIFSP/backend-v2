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
    private String nome;

    @JsonProperty("cpf")
    @NotEmpty(message = "CPF não pode estar vazio")
    @Size(max = 14, message = "CPF não pode ultrapassar 14 caracteres")
    @CPF(message = "Insira um CPF válido")
    private String cpf;

    @JsonProperty("rg")
    @NotEmpty(message = "RG não pode estar vazio")
    @Size(max = 255, message = "RG não pode ultrapassar 255 caracteres")
    private String rg;

    @JsonProperty("cep")
    @NotEmpty(message = "CEP não pode estar vazio")
    @Size(max = 9, message = "CEP não pode ultrapassar 9 caracteres")
    private String cep;

    @JsonProperty("logradouro")
    @NotEmpty(message = "Logradouro não pode estar vazio")
    @Size(max = 255, message = "Nome não pode ultrapassar 255 caracteres")
    private String logradouro;

    @JsonProperty("numero")
    @NotEmpty(message = "Número não pode estar vazio")
    @Size(max = 6, message = "Número não pode ultrapassar 6 caracteres")
    private String numero;

    @JsonProperty("bairro")
    @NotEmpty(message = "Bairro não pode estar vazio")
    @Size(max = 255, message = "Bairro não pode ultrapassar 255 caracteres")
    private String bairro;

    @JsonProperty("cidade")
    @NotEmpty(message = "Cidade não pode estar vazio")
    @Size(max = 255, message = "Cidade não pode ultrapassar 255 caracteres")
    private String cidade;

    @JsonProperty("estado")
    @NotEmpty(message = "Estado não pode estar vazio")
    @Size(max = 2, message = "Estado não pode ultrapassar 2 caracteres")
    private String estado;

    @JsonProperty("celular")
    @NotEmpty(message = "Celular não pode estar vazio")
    @Size(max = 15, message = "Celular não pode ultrapassar 15 caracteres")
    private String celular;

    @JsonProperty("telefone")
    @Size(max = 14, message = "Telefone não pode ultrapassar 14 caracteres")
    private String telefone;

    @JsonProperty("email")
    @NotEmpty(message = "E-mail não pode estar vazio")
    @Size(max = 255, message = "E-mail não pode ultrapassar 255 caracteres")
    private String email;
}

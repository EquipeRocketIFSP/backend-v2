package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record ClinicaRequestDto(
        @NotEmpty(message = "Nome Fantasia da clínica não pode ser vazio")
        @Size(max = 255, message = "Nome Fantasia da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("nome_fantasia")
        String nomeFantasia,

        @NotEmpty(message = "Razão Social da clínica não pode ser vazio")
        @Size(max = 255, message = "Razão Social da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("razao_social")
        String razaoSocial,

        @NotEmpty(message = "CNPJ da clínica não pode ser vazio")
        @Size(max = 255, message = "CNPJ da clínica não pode ser maior que 255 caracteres")
        @CNPJ(message = "O CNPJ da clínica inválido")
        String cnpj,

        @NotEmpty(message = "CNAE da clínica não pode ser vazio")
        @Size(max = 255, message = "CNAE da clínica não pode ser maior que 255 caracteres")
        String cnae,

        @NotEmpty(message = "CEP da clínica não pode ser vazio")
        @Size(max = 9, message = "CEP da clínica não pode ser maior que 9 caracteres")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP da clínica deve manter o padrão 99999-999.")
        String cep,

        @NotEmpty(message = "Logradouro da clínica não pode ser vazio")
        @Size(max = 255, message = "Logradouro da clínica não pode ser maior que 255 caracteres")
        String logradouro,

        @NotEmpty(message = "Número da clínica não pode ser vazio")
        @Size(max = 6, message = "Número da clínica não pode ser maior que 6 caracteres")
        String numero,

        @NotEmpty(message = "Bairro da clínica não pode ser vazio")
        @Size(max = 255, message = "Bairro da clínica não pode ser maior que 255 caracteres")
        String bairro,

        @NotEmpty(message = "Cidade da clínica não pode ser vazio")
        @Size(max = 255, message = "Cidade da clínica não pode ser maior que 255 caracteres")
        String cidade,

        @NotEmpty(message = "Estado da clínica não pode ser vazio")
        @Size(max = 2, message = "Estado da clínica não pode ser maior que 2 caracteres")
        String estado,

        @NotEmpty(message = "Celular da clínica não pode ser vazio")
        @Size(max = 15, message = "Celular da clínica não pode ser maior que 15 caracteres")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "O Celular da clínica deve manter o padrão (99) 99999-9999")
        String celular,

        @Size(max = 15, message = "Telefone da clínica não pode ser maior que 14 caracteres")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O Telefone da clínica deve manter o padrão (99) 9999-9999")
        String telefone,

        @NotEmpty(message = "E-mail da clínica não pode ser vazio")
        @Size(max = 255, message = "E-mail da clínica não pode ser maior que 255 caracteres")
        @Email(message = "Insira um e-mail válido")
        String email
) {
}

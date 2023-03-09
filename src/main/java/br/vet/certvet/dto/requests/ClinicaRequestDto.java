package br.vet.certvet.dto.requests;

import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ClinicaRequestDto {
    @NotEmpty(message = "Nome Fantasia da clínica não pode ser vazio")
    @Size(max = 255, message = "Nome Fantasia da clínica não pode ser maior que 255 caracteres")
    public String nome_fantasia;

    @NotEmpty(message = "Razão Social da clínica não pode ser vazio")
    @Size(max = 255, message = "Razão Social da clínica não pode ser maior que 255 caracteres")
    public String razao_social;

    @NotEmpty(message = "CNPJ da clínica não pode ser vazio")
    @Size(max = 255, message = "CNPJ da clínica não pode ser maior que 255 caracteres")
//    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}", message = "O CNPJ da clínica deve manter o padrão 99.999.999/9999-99")
    @CNPJ(message = "O CNPJ da clínica deve manter o padrão 99.999.999/9999-99")
    public String cnpj;

    @NotEmpty(message = "CNAE da clínica não pode ser vazio")
    @Size(max = 255, message = "CNAE da clínica não pode ser maior que 255 caracteres")
    public String cnae;

    @NotEmpty(message = "CEP da clínica não pode ser vazio")
    @Size(max = 9, message = "CEP da clínica não pode ser maior que 9 caracteres")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP da clínica deve manter o padrão 99999-999.")
    public String cep;

    @NotEmpty(message = "Logradouro da clínica não pode ser vazio")
    @Size(max = 255, message = "Logradouro da clínica não pode ser maior que 255 caracteres")
    public String logradouro;

    @NotEmpty(message = "Número da clínica não pode ser vazio")
    @Size(max = 6, message = "Número da clínica não pode ser maior que 6 caracteres")
    public String numero;

    @NotEmpty(message = "Bairro da clínica não pode ser vazio")
    @Size(max = 255, message = "Bairro da clínica não pode ser maior que 255 caracteres")
    public String bairro;

    @NotEmpty(message = "Cidade da clínica não pode ser vazio")
    @Size(max = 255, message = "Cidade da clínica não pode ser maior que 255 caracteres")
    public String cidade;

    @NotEmpty(message = "Estado da clínica não pode ser vazio")
    @Size(max = 2, message = "Estado da clínica não pode ser maior que 2 caracteres")
    public String estado;

    @NotEmpty(message = "Celular da clínica não pode ser vazio")
    @Size(max = 15, message = "Celular da clínica não pode ser maior que 15 caracteres")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "O Celular da clínica deve manter o padrão (99) 99999-9999")
    public String celular;

    @Size(max = 15, message = "Telefone da clínica não pode ser maior que 14 caracteres")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O Telefone da clínica deve manter o padrão (99) 9999-9999")
    public String telefone;

    @NotEmpty(message = "E-mail da clínica não pode ser vazio")
    @Size(max = 255, message = "E-mail da clínica não pode ser maior que 255 caracteres")
    @Email(message = "Insira um e-mail válido")
    public String email;
}

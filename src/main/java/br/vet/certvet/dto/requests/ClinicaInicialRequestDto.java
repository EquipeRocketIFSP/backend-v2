package br.vet.certvet.dto.requests;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ClinicaInicialRequestDto {
    @NotEmpty(message = "Nome Fantasia da clínica não pode ser vazio")
    @Size(max = 255, message = "Nome Fantasia da clínica não pode ser maior que 255 caracteres")
    public String clinica_nome_fantasia;

    @NotEmpty(message = "Razão Social da clínica não pode ser vazio")
    @Size(max = 255, message = "Razão Social da clínica não pode ser maior que 255 caracteres")
    public String clinica_razao_social;

    @NotEmpty(message = "CNPJ da clínica não pode ser vazio")
    @Size(max = 255, message = "CNPJ da clínica não pode ser maior que 255 caracteres")
//    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}", message = "O CNPJ da clínica deve manter o padrão 99.999.999/9999-99")
    @CNPJ(message = "O CNPJ da clínica deve manter o padrão 99.999.999/9999-99")
    public String clinica_cnpj;

    @NotEmpty(message = "CNAE da clínica não pode ser vazio")
    @Size(max = 255, message = "CNAE da clínica não pode ser maior que 255 caracteres")
    public String clinica_cnae;

    @NotEmpty(message = "CEP da clínica não pode ser vazio")
    @Size(max = 9, message = "CEP da clínica não pode ser maior que 9 caracteres")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP da clínica deve manter o padrão 99999-999.")
    public String clinica_cep;

    @NotEmpty(message = "Logradouro da clínica não pode ser vazio")
    @Size(max = 255, message = "Logradouro da clínica não pode ser maior que 255 caracteres")
    public String clinica_logradouro;

    @NotEmpty(message = "Número da clínica não pode ser vazio")
    @Size(max = 6, message = "Número da clínica não pode ser maior que 6 caracteres")
    public String clinica_numero;

    @NotEmpty(message = "Bairro da clínica não pode ser vazio")
    @Size(max = 255, message = "Bairro da clínica não pode ser maior que 255 caracteres")
    public String clinica_bairro;

    @NotEmpty(message = "Cidade da clínica não pode ser vazio")
    @Size(max = 255, message = "Cidade da clínica não pode ser maior que 255 caracteres")
    public String clinica_cidade;

    @NotEmpty(message = "Estado da clínica não pode ser vazio")
    @Size(max = 2, message = "Estado da clínica não pode ser maior que 2 caracteres")
    public String clinica_estado;

    @NotEmpty(message = "Celular da clínica não pode ser vazio")
    @Size(max = 15, message = "Celular da clínica não pode ser maior que 15 caracteres")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "O Celular da clínica deve manter o padrão (99) 99999-9999")
    public String clinica_celular;

    @Size(max = 15, message = "Telefone da clínica não pode ser maior que 14 caracteres")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O Telefone da clínica deve manter o padrão (99) 9999-9999")
    public String clinica_telefone;

    @NotEmpty(message = "E-mail da clínica não pode ser vazio")
    @Size(max = 255, message = "E-mail da clínica não pode ser maior que 255 caracteres")
    @Email(message = "Insira um e-mail válido")
    public String clinica_email;

    @NotEmpty(message = "Nome do dono não pode ser vazio")
    @Size(max = 255, message = "Nome do dono da clínica não pode ser maior que 255 caracteres")
    public String dono_nome;

    @NotEmpty(message = "CPF do dono não pode ser vazio")
    @Size(max = 14, message = "CPF do dono da clínica não pode ser maior que 14 caracteres")
//    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF do dono deve manter o padrão 999.999.999-99")
    @CPF(message = "O CPF do dono deve manter o padrão 999.999.999-99")
    public String dono_cpf;

    @NotEmpty(message = "RG do dono não pode ser vazio")
    @Size(max = 255, message = "RG do dono da clínica não pode ser maior que 255 caracteres")
    public String dono_rg;

    @NotEmpty(message = "CEP do dono não pode ser vazio")
    @Size(max = 9, message = "CEP do dono da clínica não pode ser maior que 9 caracteres")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP do dono deve manter o padrão 99999-999.")
    public String dono_cep;

    @NotEmpty(message = "Logradouro do dono não pode ser vazio")
    @Size(max = 255, message = "Logradouro do dono da clínica não pode ser maior que 255 caracteres")
    public String dono_logradouro;

    @NotEmpty(message = "Número do dono não pode ser vazio")
    @Size(max = 6, message = "Número do dono da clínica não pode ser maior que 6 caracteres")
    public String dono_numero;

    @NotEmpty(message = "Bairro do dono não pode ser vazio")
    @Size(max = 255, message = "Bairro do dono da clínica não pode ser maior que 255 caracteres")
    public String dono_bairro;

    @NotEmpty(message = "Cidade do dono não pode ser vazio")
    @Size(max = 255, message = "Cidade do dono da clínica não pode ser maior que 255 caracteres")
    public String dono_cidade;

    @NotEmpty(message = "Estado do dono não pode ser vazio")
    @Size(max = 2, message = "Estado do dono da clínica não pode ser maior que 2 caracteres")
    public String dono_estado;

    @NotEmpty(message = "Celular do dono não pode ser vazio")
    @Size(max = 15, message = "Celular do dono da clínica não pode ser maior que 15 caracteres")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "O Celular do dono deve manter o padrão (99) 99999-9999")
    public String dono_celular;

    @Size(max = 15, message = "Telefone do dono da clínica não pode ser maior que 14 caracteres")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O Telefone do dono deve manter o padrão (99) 9999-9999")
    public String dono_telefone;

    @NotEmpty(message = "E-mail do dono não pode ser vazio")
    @Size(max = 255, message = "E-mail do dono da clínica não pode ser maior que 255 caracteres")
    @Email(message = "Insira um e-mail válido")
    public String dono_email;

    @NotEmpty(message = "Senha do dono não pode ser vazio")
    @Size(max = 255, message = "Senha do dono da clínica não pode ser maior que 255 caracteres")
    public String dono_senha;

    @NotEmpty(message = "Nome do responsável técnico da clínica não pode ser vazio")
    @Size(max = 255, message = "Nome do responsável técnico não pode ser maior que 255 caracteres")
    public String tecnico_nome;

    @NotEmpty(message = "CPF do responsável técnico da clínica não pode ser vazio")
    @Size(max = 14, message = "CPF do responsável técnico não pode ser maior que 14 caracteres")
//    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF do responsável técnico deve manter o padrão 999.999.999-99")
    @CPF(message = "O CPF do responsável técnico deve manter o padrão 999.999.999-99")
    public String tecnico_cpf;

    @NotEmpty(message = "RG do responsável técnico da clínica não pode ser vazio")
    @Size(max = 255, message = "RG do responsável técnico não pode ser maior que 255 caracteres")
    public String tecnico_rg;

    @NotEmpty(message = "CEP do responsável técnico da clínica não pode ser vazio")
    @Size(max = 9, message = "CEP do responsável técnico não pode ser maior que 9 caracteres")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP do responsável técnico deve manter o padrão 99999-999.")
    public String tecnico_cep;

    @NotEmpty(message = "LogradouroNúmero do responsável técnico da clínica não pode ser vazio")
    @Size(max = 255, message = "LogradouroNúmero do responsável técnico não pode ser maior que 255 caracteres")
    public String tecnico_logradouro;

    @NotEmpty(message = "Número do responsável técnico da clínica não pode ser vazio")
    @Size(max = 6, message = "Número do responsável técnico não pode ser maior que 6 caracteres")
    public String tecnico_numero;

    @NotEmpty(message = "Bairro do responsável técnico da clínica não pode ser vazio")
    @Size(max = 255, message = "Bairro do responsável técnico não pode ser maior que 255 caracteres")
    public String tecnico_bairro;

    @NotEmpty(message = "Cidade do responsável técnico da clínica não pode ser vazio")
    @Size(max = 255, message = "Cidade do responsável técnico não pode ser maior que 255 caracteres")
    public String tecnico_cidade;

    @NotEmpty(message = "Estado do responsável técnico da clínica não pode ser vazio")
    @Size(max = 2, message = "Estado do responsável técnico não pode ser maior que 2 caracteres")
    public String tecnico_estado;

    @NotEmpty(message = "Celular do responsável técnico da clínica não pode ser vazio")
    @Size(max = 15, message = "Celular do responsável técnico não pode ser maior que 15 caracteres")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "O Celular da técnico deve manter o padrão (99) 99999-9999")
    public String tecnico_celular;

    @Size(max = 15, message = "Telefone do responsável técnico não pode ser maior que 14 caracteres")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O Telefone da técnico deve manter o padrão (99) 9999-9999")
    public String tecnico_telefone;

    @NotEmpty(message = "E-mail do responsável técnico da clínica não pode ser vazio")
    @Size(max = 255, message = "E-mail do responsável técnico não pode ser maior que 255 caracteres")
    @Email(message = "Insira um e-mail válido")
    public String tecnico_email;

    @NotEmpty(message = "Senha do responsável técnico da clínica não pode ser vazio")
    @Size(max = 255, message = "Senha do responsável técnico não pode ser maior que 255 caracteres")
    public String tecnico_senha;
}

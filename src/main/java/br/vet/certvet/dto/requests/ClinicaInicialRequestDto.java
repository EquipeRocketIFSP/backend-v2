package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record ClinicaInicialRequestDto(
        @NotEmpty(message = "Nome Fantasia da clínica não pode ser vazio")
        @Size(max = 255, message = "Nome Fantasia da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("clinica_nome_fantasia")
        String clinicaNomeFantasia,

        @NotEmpty(message = "Razão Social da clínica não pode ser vazio")
        @Size(max = 255, message = "Razão Social da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("clinica_razao_social")
        String clinicaRazaoSocial,

        @NotEmpty(message = "CNPJ da clínica não pode ser vazio")
        @Size(max = 18, message = "CNPJ da clínica não pode ser maior que 18 caracteres")
        @CNPJ(message = "Insira um CNPJ válido")
        @JsonProperty("clinica_cnpj")
        String clinicaCnpj,

        @NotEmpty(message = "CNAE da clínica não pode ser vazio")
        @Size(max = 255, message = "CNAE da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("clinica_cnae")
        String clinicaCnae,

        @NotEmpty(message = "CEP da clínica não pode ser vazio")
        @Size(max = 9, message = "CEP da clínica não pode ser maior que 9 caracteres")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP da clínica deve manter o padrão 99999-999.")
        @JsonProperty("clinica_cep")
        String clinicaCep,

        @NotEmpty(message = "Logradouro da clínica não pode ser vazio")
        @Size(max = 255, message = "Logradouro da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("clinica_logradouro")
        String clinicaLogradouro,

        @NotEmpty(message = "Número da clínica não pode ser vazio")
        @Size(max = 6, message = "Número da clínica não pode ser maior que 6 caracteres")
        @JsonProperty("clinica_numero")
        String clinicaNumero,

        @NotEmpty(message = "Bairro da clínica não pode ser vazio")
        @Size(max = 255, message = "Bairro da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("clinica_bairro")
        String clinicaBairro,

        @NotEmpty(message = "Cidade da clínica não pode ser vazio")
        @Size(max = 255, message = "Cidade da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("clinica_cidade")
        String clinicaCidade,

        @NotEmpty(message = "Estado da clínica não pode ser vazio")
        @Size(max = 2, message = "Estado da clínica não pode ser maior que 2 caracteres")
        @JsonProperty("clinica_estado")
        String clinicaEstado,

        @NotEmpty(message = "Celular da clínica não pode ser vazio")
        @Size(max = 15, message = "Celular da clínica não pode ser maior que 15 caracteres")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "O Celular da clínica deve manter o padrão (99) 99999-9999")
        @JsonProperty("clinica_celular")
        String clinicaCelular,

        @Size(max = 14, message = "Telefone da clínica não pode ser maior que 14 caracteres")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O Telefone da clínica deve manter o padrão (99) 9999-9999")
        @JsonProperty("clinica_telefone")
        String clinicaTelefone,

        @NotEmpty(message = "E-mail da clínica não pode ser vazio")
        @Size(max = 255, message = "E-mail da clínica não pode ser maior que 255 caracteres")
        @Email(message = "Insira um e-mail válido")
        @JsonProperty("clinica_email")
        String clinicaEmail,

        @NotEmpty(message = "Nome do dono não pode ser vazio")
        @Size(max = 255, message = "Nome do dono da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("dono_nome")
        String donoNome,

        @NotEmpty(message = "CPF do dono não pode ser vazio")
        @Size(max = 14, message = "CPF do dono da clínica não pode ser maior que 14 caracteres")
        @CPF(message = "Insira um CPF válido")
        @JsonProperty("dono_cpf")
        String donoCpf,

        @NotEmpty(message = "RG do dono não pode ser vazio")
        @Size(max = 255, message = "RG do dono da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("dono_rg")
        String donoRg,

        @NotEmpty(message = "CEP do dono não pode ser vazio")
        @Size(max = 9, message = "CEP do dono da clínica não pode ser maior que 9 caracteres")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP do dono deve manter o padrão 99999-999.")
        @JsonProperty("dono_cep")
        String donoCep,

        @NotEmpty(message = "Logradouro do dono não pode ser vazio")
        @Size(max = 255, message = "Logradouro do dono da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("dono_logradouro")
        String donoLogradouro,

        @NotEmpty(message = "Número do dono não pode ser vazio")
        @Size(max = 6, message = "Número do dono da clínica não pode ser maior que 6 caracteres")
        @JsonProperty("dono_numero")
        String donoNumero,

        @NotEmpty(message = "Bairro do dono não pode ser vazio")
        @Size(max = 255, message = "Bairro do dono da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("dono_bairro")
        String donoBairro,

        @NotEmpty(message = "Cidade do dono não pode ser vazio")
        @Size(max = 255, message = "Cidade do dono da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("dono_cidade")
        String donoCidade,

        @NotEmpty(message = "Estado do dono não pode ser vazio")
        @Size(max = 2, message = "Estado do dono da clínica não pode ser maior que 2 caracteres")
        @JsonProperty("dono_estado")
        String donoEstado,

        @NotEmpty(message = "Celular do dono não pode ser vazio")
        @Size(max = 15, message = "Celular do dono da clínica não pode ser maior que 15 caracteres")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "O Celular do dono deve manter o padrão (99) 99999-9999")
        @JsonProperty("dono_celular")
        String donoCelular,

        @Size(max = 14, message = "Telefone do dono da clínica não pode ser maior que 14 caracteres")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O Telefone do dono deve manter o padrão (99) 9999-9999")
        @JsonProperty("dono_telefone")
        String donoTelefone,

        @NotEmpty(message = "E-mail do dono não pode ser vazio")
        @Size(max = 255, message = "E-mail do dono da clínica não pode ser maior que 255 caracteres")
        @Email(message = "Insira um e-mail válido")
        @JsonProperty("dono_email")
        String donoEmail,

        @NotEmpty(message = "Senha do dono não pode ser vazio")
        @Size(max = 255, message = "Senha do dono da clínica não pode ser maior que 255 caracteres")
        @JsonProperty("dono_senha")
        String donoSenha,

        @Size(max = 255, message = "CRMV não pode ser maior que 255 caracteres")
        @JsonProperty("dono_crmv")
        String donoCrmv
) {
}

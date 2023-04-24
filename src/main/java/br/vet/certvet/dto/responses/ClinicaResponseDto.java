package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Clinica;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClinicaResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome_fantasia")
    private String nome_fantasia;

    @JsonProperty("razao_social")
    private String razao_social;

    @JsonProperty("cnpj")
    private String cnpj;

    @JsonProperty("cnae")
    private String cnae;

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

    @JsonProperty("code")
    private String code;

    public ClinicaResponseDto(Clinica clinica) {
        this.id = clinica.getId();
        this.nome_fantasia = clinica.getNomeFantasia();
        this.razao_social = clinica.getRazaoSocial();
        this.cnpj = clinica.getCnpj();
        this.cnae = clinica.getCnae();
        this.email = clinica.getEmail();
        this.cidade = clinica.getCidade();
        this.estado = clinica.getEstado();
        this.bairro = clinica.getBairro();
        this.logradouro = clinica.getLogradouro();
        this.numero = clinica.getNumero();
        this.celular = clinica.getCelular();
        this.telefone = clinica.getTelefone();
        this.cep = clinica.getCep();
        this.code = clinica.getCode();
    }
}

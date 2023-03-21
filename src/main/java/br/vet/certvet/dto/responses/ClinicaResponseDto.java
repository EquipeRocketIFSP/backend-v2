package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Clinica;

public class ClinicaResponseDto {
    public Long id;
    public String nome_fantasia;
    public String razao_social;
    public String cnpj;
    public String cnae;
    public String cep;
    public String logradouro;
    public String numero;
    public String bairro;
    public String cidade;
    public String estado;
    public String celular;
    public String telefone;
    public String email;
    public String code;

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

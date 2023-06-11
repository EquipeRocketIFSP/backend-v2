package br.vet.certvet.dto.requests;

public record CadastroTutorDto (
        String nome,
        String cpf,
        String rg,
        String cep,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String celular,
        String telefone,
        String email,
        Long clinica
){}

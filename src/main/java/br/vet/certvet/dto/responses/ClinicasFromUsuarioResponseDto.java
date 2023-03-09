package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Clinica;

public class ClinicasFromUsuarioResponseDto {
    public Long id;
    public String nome;

    public ClinicasFromUsuarioResponseDto(Clinica clinica) {
        this.id = clinica.getId();
        this.nome = clinica.getNomeFantasia();
    }
}

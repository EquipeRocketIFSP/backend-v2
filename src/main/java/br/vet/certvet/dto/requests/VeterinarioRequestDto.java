package br.vet.certvet.dto.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class VeterinarioRequestDto extends FuncionarioRequestDto{
    @NotEmpty(message = "CRMV não pode estar vazio")
    @Size(max = 255, message = "CRMV não pode ultrapassar 255 caracteres")
    public String crmv;
}

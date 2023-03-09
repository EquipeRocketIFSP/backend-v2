package br.vet.certvet.dto.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class MedicamentoRequestDto {
    @NotEmpty(message = "Por favor insira o código de registro do medicamento")
    @Size(max = 255, message = "O código de registro do medicamento não pode ultrpassar 255 caracteres")
    public String codigoRegistro;

    @NotEmpty(message = "Por favor insira o nome do medicamento")
    @Size(max = 255, message = "O nome do medicamento do medicamento não pode ultrpassar 255 caracteres")
    public String nome;

    @NotEmpty(message = "Por favor insira o princípio ativo do medicamento")
    @Size(max = 255, message = "O princípio ativo do medicamento não pode ultrpassar 255 caracteres")
    public String principioAtivo;

    @NotEmpty(message = "Por favor insira a via de uso do medicamento")
    @Size(max = 255, message = "A via de uso do medicamento não pode ultrpassar 255 caracteres")
    public String viaUso;

    @NotEmpty(message = "Por favor insira a dose do medicamento")
    @Size(max = 255, message = "A dose do medicamento do medicamento não pode ultrpassar 255 caracteres")
    public String dose;

    @NotEmpty(message = "Por favor insira a concentração do medicamento")
    @Size(max = 255, message = "A concentração do medicamento não pode ultrpassar 255 caracteres")
    public String concentracao;

    @NotEmpty(message = "Por favor insira o intervalo entre as doses do medicamento")
    @Size(max = 255, message = "O intervalo entre as doses do medicamento não pode ultrpassar 255 caracteres")
    public String intervaloDose;
}

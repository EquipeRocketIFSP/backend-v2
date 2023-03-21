package br.vet.certvet.dto.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ExameRequestDto {
    @NotNull(message = "O ID do prontuario é obrigatório")
    public Long prontuario;

    @NotEmpty(message = "Infome o tipo do exame")
    @Size(max = 255, message = "O tamanho máximo do tipo exame é de 255 caracteres")
    public String tipo_exame;

    @Size(max = 2000, message = "O tamanho máximo do campo outros é de 2000 caracteres")
    public String outros_exames;

    @Size(max = 2000, message = "O tamanho máximo do campo outros é de 2000 caracteres")
    public String outros_citologia;

    @Size(max = 255, message = "O tamanho máximo do campo bioquimico é de 255 caracteres")
    public String bioquimico;

    @Size(max = 255, message = "O tamanho máximo do campo hematologia é de 255 caracteres")
    public String hematologia;

    @Size(max = 255, message = "O tamanho máximo do campo citologia é de 255 caracteres")
    public String citologia;

    @Size(max = 255, message = "O tamanho máximo do campo imagem é de 255 caracteres")
    public String imagem;

    @Size(max = 255, message = "O tamanho máximo do campo imagem_regiao_cervical é de 255 caracteres")
    public String imagem_regiao_cervical;

    @Size(max = 255, message = "O tamanho máximo do campo imagem_regiao_abdomen é de 255 caracteres")
    public String imagem_regiao_abdomen;

    @Size(max = 255, message = "O tamanho máximo do campo imagem_regiao_mtoracicos é de 255 caracteres")
    public String imagem_regiao_mtoracicos;

    @Size(max = 255, message = "O tamanho máximo do campo imagem_regiao_mpelvicos é de 255 caracteres")
    public String imagem_regiao_mpelvicos;

    @NotNull(message = "Selecione uma opção")
    public boolean imagem_regiao_cabeca;

    @NotNull(message = "Selecione uma opção")
    public boolean imagem_regiao_torax;
}

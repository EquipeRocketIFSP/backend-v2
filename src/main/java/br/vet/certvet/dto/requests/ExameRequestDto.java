package br.vet.certvet.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ExameRequestDto {
    @NotNull(message = "O ID do prontuario é obrigatório")
    public Long prontuario;

    @NotEmpty(message = "Infome o tipo do exame")
    @Size(max = 255, message = "O tamanho máximo do tipo exame é de 255 caracteres")
    @JsonProperty("tipo_exame")
    public String tipoExame;

    @Size(max = 2000, message = "O tamanho máximo do campo outros é de 2000 caracteres")
    @JsonProperty("outros_exames")
    public String outrosExames;

    @Size(max = 2000, message = "O tamanho máximo do campo outros é de 2000 caracteres")
    @JsonProperty("outros_citologia")
    public String outrosCitologia;

    @Size(max = 255, message = "O tamanho máximo do campo bioquimico é de 255 caracteres")
    public String bioquimico;

    @Size(max = 255, message = "O tamanho máximo do campo hematologia é de 255 caracteres")
    public String hematologia;

    @Size(max = 255, message = "O tamanho máximo do campo citologia é de 255 caracteres")
    public String citologia;

    @Size(max = 255, message = "O tamanho máximo do campo imagem é de 255 caracteres")
    public String imagem;

    @Size(max = 255, message = "O tamanho máximo do campo imagem_regiao_cervical é de 255 caracteres")
    @JsonProperty("imagem_regiao_cervical")
    public String imagemRegiaoCervical;

    @Size(max = 255, message = "O tamanho máximo do campo imagem_regiao_abdomen é de 255 caracteres")
    @JsonProperty("imagem_regiao_abdomen")
    public String imagemRegiaoAbdomen;

    @Size(max = 255, message = "O tamanho máximo do campo imagem_regiao_mtoracicos é de 255 caracteres")
    @JsonProperty("imagem_regiao_mtoracicos")
    public String imagemRegiaoMToracicos;

    @Size(max = 255, message = "O tamanho máximo do campo imagem_regiao_mpelvicos é de 255 caracteres")
    @JsonProperty("imagem_regiao_mpelvicos")
    public String imagemRegiaoMPelvicos;

    @NotNull(message = "Selecione uma opção")
    @JsonProperty("imagem_regiao_cabeca")
    public boolean imagemRegiaoCabeca;

    @NotNull(message = "Selecione uma opção")
    @JsonProperty("imagem_regiao_torax")
    public boolean imagemRegiaoTorax;
}

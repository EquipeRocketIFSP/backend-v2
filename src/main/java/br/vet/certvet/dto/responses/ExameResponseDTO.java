package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Exame;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExameResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("tipo_exame")
    private String tipoExame;

    @JsonProperty("outros_exames")
    private String outrosExames;

    @JsonProperty("outros_citologia")
    private String outrosCitologia;

    @JsonProperty("bioquimico")
    private String bioquimico;

    @JsonProperty("hematologia")
    private String hematologia;

    @JsonProperty("citologia")
    private String citologia;

    @JsonProperty("imagem")
    private String imagem;

    @JsonProperty("regioes_obs")
    private String imagemObsRegioes;

    @JsonProperty("coluna")
    private String[] imagemRegiaoColuna = {};

    @JsonProperty("abdomen")
    private String[] imagemRegiaoAbdomen = {};

    @JsonProperty("m_toracicos")
    private String[] imagemRegiaoMToracicos = {};

    @JsonProperty("m_pelvicos")
    private String[] imagemRegiaoMPelvicos = {};

    @JsonProperty("cabeca")
    private boolean imagemRegiaoCabeca;

    @JsonProperty("torax")
    private boolean imagemRegiaoTorax;

    public ExameResponseDTO(Exame exame) {
        this.id = exame.getId();
        this.tipoExame = exame.getTipoExame();
        this.outrosExames = exame.getOutrosExames();
        this.outrosCitologia = exame.getOutrosCitologia();
        this.bioquimico = exame.getBioquimico();
        this.hematologia = exame.getHematologia();
        this.citologia = exame.getCitologia();
        this.imagem = exame.getImagem();
        this.imagemObsRegioes = exame.getImagemObsRegioes();
        this.imagemRegiaoCabeca = exame.isImagemRegiaoCabeca();
        this.imagemRegiaoTorax = exame.isImagemRegiaoTorax();

        if (exame.getImagemRegiaoCervical() != null)
            this.imagemRegiaoColuna = exame.getImagemRegiaoCervical().split(";");

        if (exame.getImagemRegiaoAbdomen() != null)
            this.imagemRegiaoAbdomen = exame.getImagemRegiaoAbdomen().split(";");

        if (exame.getImagemRegiaoMToracicos() != null)
            this.imagemRegiaoMToracicos = exame.getImagemRegiaoMToracicos().split(";");

        if (exame.getImagemRegiaoMPelvicos() != null)
            this.imagemRegiaoMPelvicos = exame.getImagemRegiaoMPelvicos().split(";");
    }
}

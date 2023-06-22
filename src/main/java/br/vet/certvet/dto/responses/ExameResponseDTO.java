package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Exame;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class ExameResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("tipo_exame")
    private String tipoExame;

    @JsonProperty("subtipo_exame")
    private Optional<String> subTipoExame;

    @JsonProperty("exames_outros")
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

        if (exame.getTipoExame().getPai() == null) {
            this.tipoExame = exame.getTipoExame().getNome();
            this.subTipoExame = Optional.empty();
        } else {
            this.tipoExame = exame.getTipoExame().getPai().getNome();
            this.subTipoExame = Optional.of(exame.getTipoExame().getNome());
        }

        this.outrosExames = exame.getOutrosExames();
        this.outrosCitologia = exame.getOutrosCitologia();
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

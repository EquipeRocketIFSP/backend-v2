package br.vet.certvet.dto.requests.prontuario;

import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Prontuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@ToString
public class MedicacaoPrescritaDTO {
    @JsonProperty("uso")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String uso;

    @JsonProperty("nome")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String nome = "";

    @JsonProperty("dose")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String dose = "";

    @JsonProperty("forma_farmaceutica")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String formaFarmaceutica = "";

    @JsonProperty("concentracao")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String concentracao = "";

    @JsonProperty("frequencia")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String frequencia = "";

    @JsonProperty("duracao")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String duracao = "";

    @JsonProperty("quando_aplicar")
    @NotEmpty(message = "Esse campo é obrigatório")
    @Size(max = 255, message = "Tamanho máximo desse campo é 255 caracteres")
    private String quandoAplicar = "";

    @JsonProperty("observacoes")
    @Size(max = 2000, message = "Tamanho máximo desse campo é 2000 caracteres")
    private String observacoes = "";

    public MedicacaoPrescritaDTO of(Prescricao prescricao) {
        this.uso = prescricao.getUso();
        this.nome = prescricao.getNome();
        this.dose = prescricao.getDose();
        this.formaFarmaceutica = prescricao.getFormaFarmaceutica();
        this.concentracao = prescricao.getConcentracao();
        this.frequencia = prescricao.getFrequencia();
        this.duracao = prescricao.getDuracao();
        this.quandoAplicar = prescricao.getQuandoAplicar();
        this.observacoes = prescricao.getObservacoes();
        return this;
    }

    public Prescricao translate(Prontuario prontuario) {
        return Prescricao.builder()
                .codigo(prontuario.getPrescricaoCodigo())
                .uso(this.uso)
                .nome(this.nome)
                .dose(this.dose)
                .formaFarmaceutica(this.formaFarmaceutica)
                .concentracao(this.concentracao)
                .frequencia(this.frequencia)
                .duracao(this.duracao)
                .quandoAplicar(this.quandoAplicar)
                .observacoes(this.observacoes)
                .build();
    }
}

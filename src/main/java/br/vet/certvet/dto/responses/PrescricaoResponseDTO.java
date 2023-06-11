package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Prescricao;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PrescricaoResponseDTO {
    @JsonProperty("uso")
    private String uso;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("dose")
    private String dose;

    @JsonProperty("forma_farmaceutica")
    private String formaFarmaceutica;

    @JsonProperty("concentracao")
    private String concentracao;

    @JsonProperty("frequencia")
    private String frequencia;

    @JsonProperty("duracao")
    private String duracao;

    @JsonProperty("quando_aplicar")
    private String quandoAplicar;

    @JsonProperty("observacoes")
    private String observacoes;

    public PrescricaoResponseDTO(Prescricao prescricao) {
        this.nome = prescricao.getNome();
        this.dose = prescricao.getDose();
        this.duracao = prescricao.getDuracao();
        this.concentracao = prescricao.getConcentracao();
        this.uso = prescricao.getUso();
        this.formaFarmaceutica = prescricao.getFormaFarmaceutica();
        this.frequencia = prescricao.getFrequencia();
        this.quandoAplicar = prescricao.getQuandoAplicar();
        this.observacoes = prescricao.getObservacoes();
    }
}
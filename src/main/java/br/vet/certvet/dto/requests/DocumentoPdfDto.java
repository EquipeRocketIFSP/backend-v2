package br.vet.certvet.dto.requests;

import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;

public record DocumentoPdfDto(
        @JsonProperty("observacoes_veterinario")
        String observacoesVeterinario,
        @JsonProperty("observacoes_tutor")
        String observacoesResponsavel,
        @JsonProperty("causa_mortis")
        String causaMortis,
        @JsonProperty("orienta_destino_corpo")
        String orientaDestinoCorpo,
        @JsonProperty("outras_obseracoes")
        String outrasObservacoes,
        @JsonProperty("anestesia_aplicada")
        String anestesiaAplicada,
        @JsonProperty("descricao_causa_mortis")
        String causaMortisDescription,
        @JsonProperty("data_hora_obito")
        LocalDateTime dataHoraObito,
        @JsonProperty("cidade_obito")
        String cidadeObito,
        @JsonProperty("descricao_risco_terapeutico")
        String riscoTerapeutico
) {
        public Documento toDocumento(final Prontuario prontuario, final String tipo) {
                return Documento.builder()
                        .clinica(prontuario.getClinica())
                        .prontuario(prontuario)
                        .anestesia(anestesiaAplicada())
                        .causaMortisDescription(causaMortisDescription())
                        .causaMortis(causaMortis())
                        .criadoEm(new Date())
                        .observacaoTutor(observacoesResponsavel())
                        .observacaoVet(observacoesVeterinario())
                        .local(cidadeObito())
                        .dataHoraObito(dataHoraObito())
                        .tipo(tipo)
                        .terapia(riscoTerapeutico())
                        .build()
                        .setCodigo(LocalDateTime.now());
        }
}

package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IcpReportInitialReport(
        Integer qtdAnchorsSign,
        Integer qtdSignatures,
        String fileType
) {
}

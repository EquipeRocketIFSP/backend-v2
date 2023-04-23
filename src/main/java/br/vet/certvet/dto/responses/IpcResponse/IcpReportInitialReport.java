package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IcpReportInitialReport(
        Integer qtdAnchorsSign,
        Integer qtdSignatures,
        String fileType
) {
}

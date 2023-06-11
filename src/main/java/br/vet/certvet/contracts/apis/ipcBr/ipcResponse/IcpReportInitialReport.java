package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IcpReportInitialReport(
        Integer qtdAnchorsSign,
        Integer qtdSignatures,
        String fileType
) {
}

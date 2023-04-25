package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IcpReportSoftware(
        String name,
        String sourceFileHash,
        String version,
        String sourceFile
) {
}

package br.vet.certvet.contracts.apis.ipc_br.ipc_response;

import lombok.Builder;

@Builder
public record IcpReportSoftware(
        String name,
        String sourceFileHash,
        String version,
        String sourceFile
) {
}

package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IcpReportSoftware(
        String name,
        String sourceFileHash,
        String version,
        String sourceFile
) {
}

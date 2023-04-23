package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IcpReportSoftware(
        String name,
        String sourceFileHash,
        String version,
        String sourceFile
) {
}

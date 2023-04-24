package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IpcPaRules(
        String prohibited,
        String mandatedCertificateInfo,
        String required
) {
}

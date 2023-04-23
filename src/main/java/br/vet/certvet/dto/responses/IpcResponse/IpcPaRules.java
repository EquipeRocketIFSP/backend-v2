package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IpcPaRules(
        String prohibited,
        String mandatedCertificateInfo,
        String required
) {
}

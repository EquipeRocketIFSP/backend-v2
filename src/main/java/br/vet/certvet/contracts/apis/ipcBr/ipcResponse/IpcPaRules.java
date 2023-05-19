package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IpcPaRules(
        String prohibited,
        String mandatedCertificateInfo,
        String required
) {
}

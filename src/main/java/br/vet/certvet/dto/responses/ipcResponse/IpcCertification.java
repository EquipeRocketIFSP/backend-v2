package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IpcCertification(
        String timeStamps,
        IpcSigner signer
) {
}

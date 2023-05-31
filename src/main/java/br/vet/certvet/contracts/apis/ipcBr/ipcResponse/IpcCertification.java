package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IpcCertification(
        String timeStamps,
        IpcSigner signer
) {
}

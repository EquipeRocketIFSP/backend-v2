package br.vet.certvet.dto.responses.IpcResponse;

import IpcSigner;
import lombok.Builder;

@Builder
public record IpcCertification(
        String timeStamps,
        IpcSigner signer
) {
}

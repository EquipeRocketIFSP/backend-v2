package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IpcIntegrity(
        Boolean schema,
        String messageDigest,
        String references,
        String asymmetricCipher,
        String schemaPattern,
        String hash
) {
}

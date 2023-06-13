package br.vet.certvet.contracts.apis.ipc_br.ipc_response;

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

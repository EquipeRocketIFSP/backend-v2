package br.vet.certvet.dto.responses.IpcResponse;

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

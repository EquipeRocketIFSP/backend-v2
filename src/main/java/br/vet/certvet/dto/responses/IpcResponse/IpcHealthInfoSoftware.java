package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IpcHealthInfoSoftware(
        String name,
        String version
) {
}

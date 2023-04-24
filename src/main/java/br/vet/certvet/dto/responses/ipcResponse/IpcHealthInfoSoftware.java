package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IpcHealthInfoSoftware(
        String name,
        String version
) {
}

package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IpcGeneralName(
        String name,
        String value
) {
}
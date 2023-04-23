package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IpcGeneralName(
        String name,
        String value
) {
}

package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record OptionalAttribute(
        String name,
        String status
) {
}

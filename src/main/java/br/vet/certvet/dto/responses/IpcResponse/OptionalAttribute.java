package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record OptionalAttribute(
        String name,
        String status
) {
}

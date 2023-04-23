package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record RequiredAttribute(
        String name,
        String status
) {
}

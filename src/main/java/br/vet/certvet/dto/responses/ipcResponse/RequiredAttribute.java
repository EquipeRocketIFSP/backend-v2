package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record RequiredAttribute(
        String name,
        String status
) {
}

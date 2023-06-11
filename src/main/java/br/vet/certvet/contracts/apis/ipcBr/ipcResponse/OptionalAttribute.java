package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record OptionalAttribute(
        String name,
        String status
) {
}

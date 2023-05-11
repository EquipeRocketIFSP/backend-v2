package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record RequiredAttribute(
        String name,
        String status
) {
}

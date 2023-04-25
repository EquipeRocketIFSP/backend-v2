package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IpcHealthInfoSoftware(
        String name,
        String version
) {
}

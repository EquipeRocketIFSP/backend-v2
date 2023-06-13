package br.vet.certvet.contracts.apis.ipc_br.ipc_response;

import lombok.Builder;

@Builder
public record IpcHealthInfoSoftware(
        String name,
        String version
) {
}

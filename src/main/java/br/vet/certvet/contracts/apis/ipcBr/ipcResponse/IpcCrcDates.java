package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IpcCrcDates(
        String thisUpdate,
        String nextUpdate
) {
}

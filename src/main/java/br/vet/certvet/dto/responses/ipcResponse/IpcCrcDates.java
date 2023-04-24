package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IpcCrcDates(
        String thisUpdate,
        String nextUpdate
) {
}

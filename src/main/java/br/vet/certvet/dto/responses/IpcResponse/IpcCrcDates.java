package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IpcCrcDates(
        String thisUpdate,
        String nextUpdate
) {
}

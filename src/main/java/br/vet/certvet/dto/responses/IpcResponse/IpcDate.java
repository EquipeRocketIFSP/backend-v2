package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IpcDate(
        String ipcDate
) {
}

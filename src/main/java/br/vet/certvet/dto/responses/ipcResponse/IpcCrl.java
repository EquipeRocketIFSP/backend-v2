package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IpcCrl(
        Boolean validSignature,
        String serialNumber,
        String issuerName,
        Boolean online,
        IpcCrcDates dates
) {
}

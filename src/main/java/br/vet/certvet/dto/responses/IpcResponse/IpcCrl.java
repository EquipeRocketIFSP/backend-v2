package br.vet.certvet.dto.responses.IpcResponse;

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

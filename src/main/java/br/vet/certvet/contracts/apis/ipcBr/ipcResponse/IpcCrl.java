package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

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

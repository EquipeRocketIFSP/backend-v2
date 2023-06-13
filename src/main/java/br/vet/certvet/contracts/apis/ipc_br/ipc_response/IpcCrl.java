package br.vet.certvet.contracts.apis.ipc_br.ipc_response;

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

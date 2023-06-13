package br.vet.certvet.contracts.apis.ipc_br.ipc_response;

import lombok.Builder;

@Builder
public record IpcReportDate(
        String sourceOfDate,
        String verificationDate
) {
}

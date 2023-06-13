package br.vet.certvet.contracts.apis.ipc_br.ipc_response;

import lombok.Builder;

@Builder
public record IpcReport(
        IpcReportDate date,
        String generalStatus,
        Integer number,
        IcpReportSoftware software,
        IcpReportInitialReport initialReport,
        Boolean onlyVerifyAnchored,
        Boolean extendedReport,
        IcpReportSignatures signatures
) {
}

package br.vet.certvet.dto.responses.IpcResponse;

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

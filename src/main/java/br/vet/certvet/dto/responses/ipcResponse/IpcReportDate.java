package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IpcReportDate(
        String sourceOfDate,
        String verificationDate
) {
}
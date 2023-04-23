package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IpcReportDate(
        String sourceOfDate,
        String verificationDate
) {
}

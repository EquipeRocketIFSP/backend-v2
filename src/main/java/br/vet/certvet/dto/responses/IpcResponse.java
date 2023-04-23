package br.vet.certvet.dto.responses;

import lombok.Builder;

@Builder
public record IpcResponse(
        IpcReport report,
        String receipt,
        IpcHealthInfo healthInfo
){}

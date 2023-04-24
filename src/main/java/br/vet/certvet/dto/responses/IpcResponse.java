package br.vet.certvet.dto.responses;

import br.vet.certvet.dto.responses.ipcResponse.IpcHealthInfo;
import br.vet.certvet.dto.responses.ipcResponse.IpcReport;
import lombok.Builder;

@Builder
public record IpcResponse(
        IpcReport report,
        String receipt,
        IpcHealthInfo healthInfo
){}

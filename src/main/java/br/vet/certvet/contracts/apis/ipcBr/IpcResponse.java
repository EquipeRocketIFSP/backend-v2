package br.vet.certvet.contracts.apis.ipcBr;

import br.vet.certvet.contracts.apis.ipcBr.ipcResponse.IpcHealthInfo;
import br.vet.certvet.contracts.apis.ipcBr.ipcResponse.IpcReport;
import lombok.Builder;

@Builder
public record IpcResponse(
        IpcReport report,
        String receipt,
        IpcHealthInfo healthInfo
){}

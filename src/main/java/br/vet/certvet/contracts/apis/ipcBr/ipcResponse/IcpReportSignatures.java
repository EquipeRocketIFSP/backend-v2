package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

import java.util.List;

@Builder
public record IcpReportSignatures(
        List<IpcReportSignature> signature
) {
}

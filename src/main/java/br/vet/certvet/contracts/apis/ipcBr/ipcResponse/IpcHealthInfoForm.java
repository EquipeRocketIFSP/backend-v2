package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;


import lombok.Builder;

@Builder
public record IpcHealthInfoForm(
        IpcEntry entry,
        String medicationDispenseString
) {
}

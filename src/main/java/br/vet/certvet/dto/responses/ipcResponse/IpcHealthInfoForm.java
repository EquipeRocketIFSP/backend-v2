package br.vet.certvet.dto.responses.ipcResponse;


import lombok.Builder;

@Builder
public record IpcHealthInfoForm(
        IpcEntry entry,
        String medicationDispenseString
) {
}

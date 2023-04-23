package br.vet.certvet.dto.responses.IpcResponse;


import lombok.Builder;

@Builder
public record IpcHealthInfoForm(
        IpcEntry entry,
        String medicationDispenseString
) {
}

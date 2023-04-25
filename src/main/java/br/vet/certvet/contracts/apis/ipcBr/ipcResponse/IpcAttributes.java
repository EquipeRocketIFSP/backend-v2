package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IpcAttributes(
        RequiredAttributes requiredAttributes,
        String ignoredAttributes,
        IpcOptionalAttributes optionalAttributes,
        String extraAttributes
) {
}

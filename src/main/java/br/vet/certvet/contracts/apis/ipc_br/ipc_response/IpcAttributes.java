package br.vet.certvet.contracts.apis.ipc_br.ipc_response;

import lombok.Builder;

@Builder
public record IpcAttributes(
        RequiredAttributes requiredAttributes,
        String ignoredAttributes,
        IpcOptionalAttributes optionalAttributes,
        String extraAttributes
) {
}

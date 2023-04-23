package br.vet.certvet.dto.responses.IpcResponse;

import RequiredAttributes;
import lombok.Builder;

@Builder
public record IpcAttributes(
        RequiredAttributes requiredAttributes,
        String ignoredAttributes,
        IpcOptionalAttributes optionalAttributes,
        String extraAttributes
) {
}

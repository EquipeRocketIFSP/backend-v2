package br.vet.certvet.dto.responses.IpcResponse;

import OptionalAttribute;
import lombok.Builder;

import java.util.List;

@Builder
public record IpcOptionalAttributes(
        List<OptionalAttribute> optionalAttribute
) {
}

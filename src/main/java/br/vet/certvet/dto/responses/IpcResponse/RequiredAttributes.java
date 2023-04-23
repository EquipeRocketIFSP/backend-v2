package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

import java.util.List;

@Builder
public record RequiredAttributes(
        List<RequiredAttribute> requiredAttribute
) {
}

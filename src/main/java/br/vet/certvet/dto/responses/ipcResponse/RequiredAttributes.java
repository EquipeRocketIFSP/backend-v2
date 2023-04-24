package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

import java.util.List;

@Builder
public record RequiredAttributes(
        List<RequiredAttribute> requiredAttribute
) {
}

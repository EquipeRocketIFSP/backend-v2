package br.vet.certvet.contracts.apis.ipc_br.ipc_response;

import lombok.Builder;

import java.util.List;

@Builder
public record RequiredAttributes(
        List<RequiredAttribute> requiredAttribute
) {
}

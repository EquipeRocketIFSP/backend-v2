package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IpcReportSignature(
        Boolean containsMandatedCertificates,
        Boolean attributeValid,
        String errorMessages,
        String warningMessages,
        Boolean hasInvalidUpdates,
        String signaturePolicy,
        IpcIntegrity integrity,
        String signatureType,
        IpcAttributes attributes,
        IpcPaRules paRules,
        String signingTime,
        IpcCertification certification
) {
}

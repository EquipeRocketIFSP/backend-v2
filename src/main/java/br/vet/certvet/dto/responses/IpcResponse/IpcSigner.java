package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

import java.util.List;

@Builder
public record IpcSigner(
        IpcExtensions extensions,
        String validSignature,
        String form,
        List<IpcCertificate> certificate,
        String certPathValid,
        String flagCountry,
        Boolean present,
        String subjectName
) {
}

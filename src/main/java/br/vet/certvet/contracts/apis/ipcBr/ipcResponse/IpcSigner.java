package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

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

package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IpcCertificate(
        String notAfter,
        Boolean validSignature,
        String serialNumber,
        Boolean expired,
        String issuerName,
        Boolean online,
        IpcCrl crl,
        Boolean revoked,
        String notBefore,
        String subjectName
) {
}

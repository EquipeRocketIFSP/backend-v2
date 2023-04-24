package br.vet.certvet.dto.responses.ipcResponse;

import lombok.Builder;

@Builder
public record IpcHealthInfoPrescriber(
        String profession,
        String UF,
        String signatureHash,
        String validSignature,
        String statusSigner,
        String cpf,
        String professionalCode
) {
}

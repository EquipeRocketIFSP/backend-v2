package br.vet.certvet.contracts.apis.ipc_br.ipc_response;

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

package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

@Builder
public record IpcHealthInfoPharmacist(
        String profession,
        String UF,
        String signatureHash,
        String validSignature,
        String statusSigner,
        String cpf,
        String professionalCode
) {
}

package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IpcEntry(
        String cpf_second_signer,
        String cpf_first_signer,
        String signed_date_first_signer,
        String signed_date_second_signer
) {
}

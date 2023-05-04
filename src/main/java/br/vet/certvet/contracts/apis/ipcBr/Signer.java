package br.vet.certvet.contracts.apis.ipcBr;

import java.time.LocalDate;

public record Signer(
        String signerCpf,
        LocalDate date
) {
}

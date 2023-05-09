package br.vet.certvet.contracts.apis.ipcBr;

import java.time.LocalDate;

public record Signer(
        String signerCpf,
        LocalDate date
) {
    @Override
    public String signerCpf() {
        return "%s.%s.%s-%s".formatted(
                signerCpf.substring(0, 3),
                signerCpf.substring(3, 6),
                signerCpf.substring(6, 9),
                signerCpf.substring(9)
                );
    }
}

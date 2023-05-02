package br.vet.certvet.enums;

public enum TransacaoStatus {
    ENTRY(false), EXIT(true);

    private final boolean status;

    private TransacaoStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return this.status;
    }
}

package br.vet.certvet.enums;

public enum ProntuarioStatus {
    PENDING("Pending"), UPDATING("Updating"), COMPLETED("Completed");

    private final String status;

    private ProntuarioStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}

package br.vet.certvet.exceptions.specializations.agendamento;

import br.vet.certvet.exceptions.ConflictException;

public class AgendamentoConflictException extends ConflictException {
    public AgendamentoConflictException() {
        super();
    }

    public AgendamentoConflictException(String message) {
        super(message);
    }
}

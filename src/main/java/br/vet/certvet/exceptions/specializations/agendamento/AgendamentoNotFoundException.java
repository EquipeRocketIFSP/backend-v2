package br.vet.certvet.exceptions.specializations.agendamento;

import br.vet.certvet.exceptions.NotFoundException;

public class AgendamentoNotFoundException extends NotFoundException {
    public AgendamentoNotFoundException() {
        super("Agendamento não encontrado");
    }

    public AgendamentoNotFoundException(String message) {
        super(message);
    }
}

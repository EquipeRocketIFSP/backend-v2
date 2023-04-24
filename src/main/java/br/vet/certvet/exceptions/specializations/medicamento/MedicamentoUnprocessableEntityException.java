package br.vet.certvet.exceptions.specializations.medicamento;

import br.vet.certvet.exceptions.UnprocessableEntityException;

public class MedicamentoUnprocessableEntityException extends UnprocessableEntityException {
    public MedicamentoUnprocessableEntityException(String message) {
        super(message);
    }
}

package br.vet.certvet.services;

import br.vet.certvet.contracts.apis.anvisa.MedicationAPIResponse;

public interface AnvisaAPIService {
    String getProcessNumberByRegisterNumber(String registerNumber);

    MedicationAPIResponse getMedicationsByProcessNumber(String processNumber);
}

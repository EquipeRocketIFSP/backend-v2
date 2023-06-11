package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyPrescribedException extends RuntimeException {
    public AlreadyPrescribedException(String messgge) {
        super(messgge);
    }
}

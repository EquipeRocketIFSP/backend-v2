package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClinicaNotFoundException extends RuntimeException {
    public ClinicaNotFoundException(String message) {
        super(message);
    }
}

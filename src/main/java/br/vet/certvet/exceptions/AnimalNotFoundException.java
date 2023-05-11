package br.vet.certvet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(String message) {
        super(message);
    }
}

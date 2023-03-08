package br.vet.certvet.services;

import br.vet.certvet.dto.requests.PasswordRequestDto;

public interface PasswordResetService {
    void sendPasswordResetEmail(PasswordRequestDto dto);
}

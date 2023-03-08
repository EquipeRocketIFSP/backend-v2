package br.vet.certvet.services;

import br.vet.certvet.dto.requests.PasswordResetEmailRequestDto;

public interface PasswordResetService {
    void sendPasswordResetEmail(PasswordResetEmailRequestDto dto);
}

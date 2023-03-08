package br.vet.certvet.services;

import br.vet.certvet.dto.requests.PasswordResetEmailRequestDto;
import br.vet.certvet.dto.requests.PasswordResetRequestDto;

public interface PasswordResetService {
    void sendPasswordResetEmail(PasswordResetEmailRequestDto dto);

    void resetPassword(PasswordResetRequestDto dto);
}

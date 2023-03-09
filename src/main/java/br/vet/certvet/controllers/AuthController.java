package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.dto.requests.LoginRequestDto;
import br.vet.certvet.dto.requests.PasswordResetEmailRequestDto;
import br.vet.certvet.dto.requests.PasswordResetRequestDto;
import br.vet.certvet.dto.responses.TokenResponseDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.PasswordResetService;
import br.vet.certvet.services.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class AuthController {
    @Autowired
    private ClinicaService clinicaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/auth")
    @SecurityRequirements(value = {})
    public ResponseEntity<TokenResponseDto> authenticate(@Validated @RequestBody LoginRequestDto dto) {
        Authentication auth = authenticationManager.authenticate(dto.convert());
        Clinica clinica = this.clinicaService.findById(dto.clinica);
        String token = tokenService.create(auth, clinica);
        Usuario usuario = usuarioService.findOne(dto.email, clinica);

        this.tokenService.getUsuarioId(token);

        TokenResponseDto tokenDto = TokenResponseDto.builder()
                .token(token)
                .type("Bearer")
                .nome(usuario.getNome())
                .build();

        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/esqueci-minha-senha")
    public ResponseEntity<Void> formPasswordResetRequest(@Valid @RequestBody PasswordResetEmailRequestDto dto) {
        this.passwordResetService.sendPasswordResetEmail(dto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinePassword(@Valid @RequestBody PasswordResetRequestDto dto) {
        this.passwordResetService.resetPassword(dto);

        return ResponseEntity.ok().build();
    }
}

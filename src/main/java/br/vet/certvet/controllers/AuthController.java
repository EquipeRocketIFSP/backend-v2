package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.dto.requests.LoginRequestDto;
import br.vet.certvet.dto.responses.TokenResponseDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/auth")
    @SecurityRequirements(value = {})
    public ResponseEntity<TokenResponseDto> authenticate(@Validated @RequestBody LoginRequestDto dto) {
        Authentication auth = authenticationManager.authenticate(dto.convert());
        Clinica clinica = this.clinicaService.findByCnpj(dto.cnpj_clinica);
        String token = tokenService.create(auth, clinica);
        Usuario usuario = usuarioService.findByUsername(dto.email, clinica);

        this.tokenService.getUsuarioId(token);

        TokenResponseDto tokenDto = TokenResponseDto.builder()
                .token(token)
                .type("Bearer")
                .nome(usuario.getNome())
                .build();

        return ResponseEntity.ok(tokenDto);
    }
}

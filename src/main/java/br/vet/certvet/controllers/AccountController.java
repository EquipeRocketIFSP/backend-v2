package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.dto.responses.UsuarioResponseDto;
import br.vet.certvet.dto.responses.VeterinarioResponseDto;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping("api/usuario")
@SecurityRequirement(name = "bearer-key")
public class AccountController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PutMapping("/funcionario")
    public ResponseEntity<UsuarioResponseDto> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid FuncionarioRequestDto dto
    ) {
        Usuario usuario = this.tokenService.getUsuario(token);
        usuario.fill(dto);

        usuario = this.usuarioService.edit(usuario);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @PutMapping("/veterinario")
    public ResponseEntity<UsuarioResponseDto> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid VeterinarioRequestDto dto
    ) {
        Usuario usuario = this.tokenService.getUsuario(token);
        usuario.fill(dto);

        usuario = this.usuarioService.edit(usuario);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @GetMapping("")
    public ResponseEntity<UsuarioResponseDto> findOne(@RequestHeader(AUTHORIZATION) String token) {
        Usuario usuario = this.tokenService.getUsuario(token);
        Optional<Authority> authorityResponse = this.usuarioService.findUsuarioAuthority(usuario, "VETERINARIO");

        if (authorityResponse.isPresent())
            return ResponseEntity.ok(new VeterinarioResponseDto(usuario));

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @GetMapping("autoridades")
    public ResponseEntity<List<String>> findAuthorities(@RequestHeader(AUTHORIZATION) String token) {
        Usuario usuario = this.tokenService.getUsuario(token);
        List<String> authorities = usuario.getAuthorities().stream().map(Authority::getPermissao).toList();

        return ResponseEntity.ok(authorities);
    }
}

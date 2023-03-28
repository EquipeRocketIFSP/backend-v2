package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.dto.requests.*;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.dto.responses.UsuarioResponseDto;
import br.vet.certvet.dto.responses.VeterinarioResponseDto;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Slf4j
public class UsuarioController extends BaseController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClinicaService clinicaService;

    @PostMapping("/funcionario")
    public ResponseEntity<UsuarioResponseDto> create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid FuncionarioRequestDto dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario usuario = this.usuarioService.create(dto, clinica);

        return new ResponseEntity<>(new UsuarioResponseDto(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/veterinario")
    public ResponseEntity<UsuarioResponseDto> create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid VeterinarioRequestDto dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario usuario = this.usuarioService.create(dto, clinica);

        return new ResponseEntity<>(new UsuarioResponseDto(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/tutor")
    public ResponseEntity<UsuarioResponseDto> create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid UsuarioRequestDto dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario usuario = this.usuarioService.create(dto, clinica);

        return new ResponseEntity<>(new UsuarioResponseDto(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/funcionario/{id}")
    public ResponseEntity<UsuarioResponseDto> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid FuncionarioEditRequestDto dto,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario usuario = this.usuarioService.findOne(id, clinica);
        usuario = this.usuarioService.edit(dto, usuario);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @PutMapping("/veterinario/{id}")
    public ResponseEntity<UsuarioResponseDto> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid VeterinarioEditRequestDto dto,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario usuario = this.usuarioService.findOne(id, clinica);
        usuario = this.usuarioService.edit(dto, usuario);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @PutMapping("/tutor/{id}")
    public ResponseEntity<UsuarioResponseDto> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid UsuarioRequestDto dto,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario usuario = this.usuarioService.findOne(id, clinica);
        usuario = this.usuarioService.edit(dto, usuario);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @GetMapping("/usuario")
    public ResponseEntity<UsuarioResponseDto> findOne(@RequestHeader(AUTHORIZATION) String token) {
        Usuario usuario = this.tokenService.getUsuario(token);
        Optional<Authority> authorityResponse = this.usuarioService.findUsuarioAuthority(usuario, "VETERINARIO");

        if (authorityResponse.isPresent())
            return ResponseEntity.ok(new VeterinarioResponseDto(usuario));

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @GetMapping({"/funcionario/{id}", "/veterinario/{id}", "/tutor/{id}"})
    public ResponseEntity<UsuarioResponseDto> findOne(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario usuario = this.usuarioService.findOne(id, clinica);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @GetMapping({"/funcionario", "/veterinario", "/tutor"})
    public ResponseEntity<PaginatedResponse<UsuarioResponseDto>> findAllVeterinarios(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestParam(name = "pagina", defaultValue = "1") int page,
            @RequestParam(name = "buscar", defaultValue = "") String search,
            HttpServletRequest request
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        PaginatedResponse<UsuarioResponseDto> response = this.usuarioService.findAll(page, search, request.getRequestURL().toString(), clinica);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/autoridades")
    public ResponseEntity<List<String>> findAuthorities(@RequestHeader(AUTHORIZATION) String token) {
        Usuario usuario = this.tokenService.getUsuario(token);
        List<String> authorities = usuario.getAuthorities().stream().map(Authority::getAuthority).toList();

        return ResponseEntity.ok(authorities);
    }

    @DeleteMapping({"/funcionario/{id}", "/veterinario/{id}", "/tutor/{id}"})
    public ResponseEntity<Void> delete(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario usuario = this.usuarioService.findOne(id, clinica);

        this.usuarioService.delete(usuario);

        return ResponseEntity.accepted().build();
    }

    @PutMapping({"/funcionario/{id}/restaurar", "/veterinario/{id}/restaurar", "/tutor/{id}/restaurar"})
    public ResponseEntity<UsuarioResponseDto> recover(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario usuario = this.usuarioService.findOne(id, clinica);
        usuario = this.usuarioService.recover(usuario);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }
}

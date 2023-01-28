package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.dto.responses.UsuarioResponseDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
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
            @RequestBody @Valid FuncionarioRequestDto dto,
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
            @RequestBody @Valid VeterinarioRequestDto dto,
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
}

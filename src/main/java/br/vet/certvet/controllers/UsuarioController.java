package br.vet.certvet.controllers;

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
public class UsuarioController extends BaseController{
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClinicaService clinicaService;

    //TODO: pegar id da clinica pelo token de autenticação
    @PostMapping({"/clinica/{clinica}/funcionario"})
    public ResponseEntity<UsuarioResponseDto> createFuncionario(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid FuncionarioRequestDto dto
    ) {
        return null;
        //return this.create(dto, clinicaId);
    }

    @PostMapping({"/clinica/{clinica}/veterinario"})
    public ResponseEntity<UsuarioResponseDto> createVeterinario(@RequestBody @Valid VeterinarioRequestDto dto, @PathVariable("clinica") Long clinicaId) {
        return this.create(dto, clinicaId);
    }

    //TODO: pegar id da clinica pelo token de autenticação
    @PostMapping("/clinica/{clinica}/tutor")
    public ResponseEntity<UsuarioResponseDto> createTutor(@RequestBody @Valid UsuarioRequestDto dto, @PathVariable("clinica") Long clinicaId) {
        return this.create(dto, clinicaId);
    }

    @PutMapping("/clinica/{clinica}/funcionario/{id}")
    public ResponseEntity<UsuarioResponseDto> editFuncionario(
            @RequestBody @Valid FuncionarioRequestDto dto,
            @PathVariable("clinica") Long clinicaId,
            @PathVariable("id") Long id
    ) {
        Usuario usuario = this.usuarioService.findById(id);
        usuario = this.usuarioService.edit(dto, usuario);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @PutMapping("/clinica/{clinica}/veterinario/{id}")
    public ResponseEntity<UsuarioResponseDto> editVeterinario(
            @RequestBody @Valid VeterinarioRequestDto dto,
            @PathVariable("clinica") Long clinicaId,
            @PathVariable("id") Long id
    ) {
        Usuario usuario = this.usuarioService.findById(id);
        usuario = this.usuarioService.edit(dto, usuario);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    @PutMapping("/clinica/{clinica}/tutor/{id}")
    public ResponseEntity<UsuarioResponseDto> editTutor(
            @RequestBody @Valid UsuarioRequestDto dto,
            @PathVariable("clinica") Long clinicaId,
            @PathVariable("id") Long id
    ) {
        Usuario usuario = this.usuarioService.findById(id);
        usuario = this.usuarioService.edit(dto, usuario);

        return ResponseEntity.ok(new UsuarioResponseDto(usuario));
    }

    private ResponseEntity<UsuarioResponseDto> create(UsuarioRequestDto dto, Long clinicaId) {
        Clinica clinica = this.clinicaService.findById(clinicaId);
        Usuario usuario = this.usuarioService.create(dto, clinica);

        return new ResponseEntity<>(new UsuarioResponseDto(usuario), HttpStatus.CREATED);
    }
}

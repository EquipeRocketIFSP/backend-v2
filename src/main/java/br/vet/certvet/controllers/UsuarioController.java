package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.responses.UsuarioResponseDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClinicaService clinicaService;

    //TODO: pegar id da clinica pelo token de autenticação
    @PostMapping("/clinica/{clinica}/tutor")
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioRequestDto usuarioRequestDto, @PathVariable("clinica") Long clinicaId) {
        Clinica clinica = this.clinicaService.recuperar(clinicaId);
        Usuario usuario = this.usuarioService.criar(usuarioRequestDto, clinica);

        return new ResponseEntity<>(new UsuarioResponseDto(usuario), HttpStatus.CREATED);
    }
}

package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.dto.responses.AnimalResponseDto;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.AnimalService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class AnimalController extends BaseController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/animal")
    public ResponseEntity<AnimalResponseDto> create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid AnimalRequestDto dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        List<Usuario> tutores = dto.tutores.stream().map((id) -> this.usuarioService.findOne(id, clinica)).toList();
        Animal animal = this.animalService.create(dto, tutores);

        return new ResponseEntity<>(new AnimalResponseDto(animal), HttpStatus.CREATED);
    }
}

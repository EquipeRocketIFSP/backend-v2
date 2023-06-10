package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.dto.responses.AnimalResponseDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.AnimalService;
import br.vet.certvet.services.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
@CrossOrigin
@SecurityRequirement(name = "bearer-key")
public class AnimalController extends BaseController {

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
        List<Usuario> tutores = dto.getTutores().stream().map(id -> this.usuarioService.findOne(id, clinica)).toList();
        Animal animal = this.animalService.create(dto, tutores);

        return new ResponseEntity<>(new AnimalResponseDto(animal), HttpStatus.CREATED);
    }

    @PutMapping("/tutor/{tutor_id}/animal/{id}")
    public ResponseEntity<AnimalResponseDto> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid AnimalRequestDto dto,
            @PathVariable("tutor_id") Long previousTutorId,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario tutor = this.usuarioService.findOne(previousTutorId, clinica);
        List<Usuario> tutores = dto.getTutores().stream().map(tutorId -> this.usuarioService.findOne(tutorId, clinica)).toList();

        Animal animal = this.animalService.findOne(id, tutor);
        animal = this.animalService.edit(dto, animal, tutores);

        return new ResponseEntity<>(new AnimalResponseDto(animal), HttpStatus.CREATED);
    }

    @GetMapping("/tutor/{tutor_id}/animal/{id}")
    public ResponseEntity<AnimalResponseDto> findOne(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("tutor_id") Long tutorId,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario tutor = this.usuarioService.findOne(tutorId, clinica);
        Animal animal = this.animalService.findOne(id, tutor);

        return ResponseEntity.ok(new AnimalResponseDto(animal));
    }

    @GetMapping("/tutor/{tutor_id}/animal")
    public ResponseEntity<PaginatedResponse<AnimalResponseDto>> findAll(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("tutor_id") Long tutorId,
            @RequestParam(name = "pagina", defaultValue = "1") int page,
            @RequestParam(name = "buscar", defaultValue = "") String search,
            HttpServletRequest request
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario tutor = this.usuarioService.findOne(tutorId, clinica);

        PaginatedResponse<AnimalResponseDto> response = this.animalService.findAll(page, search, request.getRequestURL().toString(), tutor);

        return ResponseEntity.ok(response);
    }
}

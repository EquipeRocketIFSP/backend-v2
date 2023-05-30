package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.prontuario.exame.ExameListDTO;
import br.vet.certvet.dto.responses.ProntuarioResponseDTO;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.AnimalService;
import br.vet.certvet.services.ExameService;
import br.vet.certvet.services.ProntuarioService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping("/api/prontuario/{id}/exames")
public class ExameController extends BaseController {
    @Autowired
    private ExameService exameService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private ProntuarioService prontuarioService;

    @PutMapping
    public ResponseEntity<ProntuarioResponseDTO> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id,
            @RequestBody @Valid ExameListDTO dto
    ) {
        final Clinica clinica = this.tokenService.getClinica(token);
        final Usuario tutor = this.usuarioService.findOne(dto.getTutor(), clinica);
        final Animal animal = this.animalService.findOne(dto.getAnimal(), tutor);
        final Prontuario prontuario = this.prontuarioService.findOne(id, animal);

        this.exameService.assignToProntuario(dto, prontuario);

        return ResponseEntity.ok(new ProntuarioResponseDTO(prontuario));
    }
}

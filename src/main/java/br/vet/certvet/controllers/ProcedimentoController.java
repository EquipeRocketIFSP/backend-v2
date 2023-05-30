package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.prontuario.procedimento.ProcedimentoListDTO;
import br.vet.certvet.dto.responses.ProntuarioResponseDTO;
import br.vet.certvet.models.*;
import br.vet.certvet.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping("/api/prontuario/{id}/procedimentos")
public class ProcedimentoController extends BaseController {
    @Autowired
    private ProcedimentoService procedimentoService;

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
            @RequestBody @Valid ProcedimentoListDTO dto
    ) {
        final Clinica clinica = this.tokenService.getClinica(token);
        final Usuario tutor = this.usuarioService.findOne(dto.getTutor(), clinica);
        final Animal animal = this.animalService.findOne(dto.getAnimal(), tutor);
        final Prontuario prontuario = this.prontuarioService.findOne(id, animal);

        this.procedimentoService.assignToProntuario(dto, clinica, prontuario);

        return ResponseEntity.ok(new ProntuarioResponseDTO(prontuario));
    }
}

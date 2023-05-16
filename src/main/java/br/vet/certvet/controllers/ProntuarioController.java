package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.prontuario.*;
import br.vet.certvet.dto.responses.ProntuarioResponseDTO;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.dto.ProntuarioRequest;
import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.AnimalService;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/prontuario")
@Slf4j
public class ProntuarioController extends BaseController {

    @Autowired
    private ProntuarioService prontuarioService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PdfService pdfService;

    @PostMapping
    public ResponseEntity<ProntuarioResponseDTO> create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid SinaisVitaisDTO dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario tutor = this.usuarioService.findOne(dto.getTutor(), clinica);
        Usuario veterinario = this.usuarioService.findOne(dto.getVeterinario(), clinica);
        Animal animal = this.animalService.findOne(dto.getAnimal(), tutor);

        Prontuario prontuario = this.prontuarioService.create(dto, animal, tutor, veterinario);

        return new ResponseEntity<>(new ProntuarioResponseDTO(prontuario), HttpStatus.CREATED);
    }

    @PutMapping("{id}/sinais-vitais")
    public ResponseEntity<ProntuarioResponseDTO> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id,
            @RequestBody @Valid SinaisVitaisDTO dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario tutor = this.usuarioService.findOne(dto.getTutor(), clinica);
        Animal animal = this.animalService.findOne(dto.getAnimal(), tutor);
        Prontuario prontuario = this.prontuarioService.findOne(id, animal);
        prontuario = this.prontuarioService.edit(dto, prontuario);

        return ResponseEntity.ok(new ProntuarioResponseDTO(prontuario));
    }

    @PutMapping("{id}/supeita-diagnostica")
    public ResponseEntity<ProntuarioResponseDTO> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id,
            @RequestBody @Valid SuspeitaDiagnosticaDTO dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario tutor = this.usuarioService.findOne(dto.getTutor(), clinica);
        Animal animal = this.animalService.findOne(dto.getAnimal(), tutor);
        Prontuario prontuario = this.prontuarioService.findOne(id, animal);
        prontuario = this.prontuarioService.edit(dto, prontuario);

        return ResponseEntity.ok(new ProntuarioResponseDTO(prontuario));
    }

    @PutMapping("{id}/manifestacoes-clinicas")
    public ResponseEntity<ProntuarioResponseDTO> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id,
            @RequestBody @Valid ManifestacoesClinicasDTO dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario tutor = this.usuarioService.findOne(dto.getTutor(), clinica);
        Animal animal = this.animalService.findOne(dto.getAnimal(), tutor);
        Prontuario prontuario = this.prontuarioService.findOne(id, animal);
        prontuario = this.prontuarioService.edit(dto, prontuario);

        return ResponseEntity.ok(new ProntuarioResponseDTO(prontuario));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProntuarioResponseDTO> getProntuario(
            @PathVariable String codigo
    ) {
        Prontuario prontuario = prontuarioService.findByCodigo(codigo).orElseThrow(ProntuarioNotFoundException::new);

        return ResponseEntity.ok(new ProntuarioResponseDTO(prontuario));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> getProntuarioPdf(
            @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
            @PathVariable Long id
    ) throws IOException {
        if (!MediaType.APPLICATION_PDF_VALUE.equals(contentType))
            return ResponseEntity.badRequest()
                    .header("reason", "Media type not allowed")
                    .build();
        Optional<Prontuario> prontuario = prontuarioService.findById(id);
        if (prontuario.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfService.retrieveFromRepository(prontuario.get()));
    }

    @PostMapping("/{prontuarioId}")
    public ResponseEntity<Documento> addDocument(
            @PathVariable Long prontuarioId,
            @RequestBody ProntuarioRequest prontuarioDto
    ) {
        return null;
    }
}

package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.prontuario.SinaisVitaisDTO;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.AnimalService;
import br.vet.certvet.services.ProntuarioService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/prontuario")
public class ProntuarioController extends BaseController {
    @Autowired
    private ProntuarioService prontuarioService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid SinaisVitaisDTO dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario tutor = this.usuarioService.findOne(dto.getTutor(), clinica);
        Usuario veterinario = this.usuarioService.findOne(dto.getVeterinario(), clinica);
        Animal animal = this.animalService.findOne(dto.getAnimal(), tutor);

        this.prontuarioService.create(dto, animal, tutor, veterinario);

        return ResponseEntity.ok().build();
    }


    /*
    @Autowired
    private PdfService pdfService;

    @GetMapping("/{id}")
    public ResponseEntity<Prontuario> getProntuario(
            @PathVariable Long id
    ){
        Optional<Prontuario> prontuario = prontuarioService.findById(id);
        return prontuario.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
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
        return prontuario.isPresent()
                ? ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdfService.retrieveFromRepository(prontuario.get()))
                : ResponseEntity.notFound().build();
    }*/


}

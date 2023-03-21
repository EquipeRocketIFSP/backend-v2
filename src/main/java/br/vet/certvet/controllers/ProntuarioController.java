package br.vet.certvet.controllers;

import br.vet.certvet.dto.ProntuarioRequest;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/prontuario")
@Slf4j
public class ProntuarioController extends BaseController {

    @Autowired
    private ProntuarioService prontuarioService;
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
    }

    @PostMapping
    public ResponseEntity<Prontuario> create(
            @RequestBody ProntuarioRequest prontuarioDto
            ){

        Prontuario p = null;
        try{
            p = prontuarioDto.convert();
        } catch (RuntimeException e){
            throwExceptionFromController(e);
        }
        Optional<Prontuario> saved = prontuarioService.createProntuario(p);

        return saved.map(
                prontuario -> ResponseEntity.created(
                        URI.create(prontuario.getCodigo())
                        ).body(prontuario))
                .orElseGet(
                        () -> ResponseEntity.internalServerError().build()
                );
    }

    @PostMapping("/{prontuarioId}")
    public ResponseEntity<Documento> addDocument(
            @PathVariable Long prontuarioId,
            @RequestBody ProntuarioRequest prontuarioDto
    ){
        return null;
    }


}

package br.vet.certvet.controllers;

import br.vet.certvet.models.Prontuario;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/prontuario")
public class ProntuarioController extends BaseController {

    /*@Autowired
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
    }*/


}

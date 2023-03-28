package br.vet.certvet.controllers;

import br.vet.certvet.models.Documento;
import br.vet.certvet.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/documento")
public class DocumentoController extends BaseController {

    @Autowired
    private ProntuarioService prontuarioService;

    @GetMapping("/{tipo}/{prontuarioId}")
    public ResponseEntity<List<Documento>> getDocumentosByTipo(
            @PathVariable String tipo,
            @PathVariable Long prontuarioId
    ){
        List<Documento> documentosSanitarios = prontuarioService.getDocumentosTipo(prontuarioId, tipo);
        return documentosSanitarios.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(documentosSanitarios);
    }

}

package br.vet.certvet.controllers;

import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/documento")
public class DocumentoController extends BaseController {

    @Autowired
    private ProntuarioService prontuarioService;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/{tipo}/{prontuarioId}")
    public ResponseEntity<List<Documento>> getDocumentosByTipo(
            @PathVariable String tipo,
            @PathVariable Long prontuarioId
    ){
        List<Documento> documentosProntuario = prontuarioService.getDocumentosByTipo(prontuarioId, tipo);
        return documentosProntuario.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(documentosProntuario);
    }

    @GetMapping("/{tipo}/{prontuarioId}/novo")
    public ResponseEntity<byte[]> getDocumentoEmBranco(
            @PathVariable String tipo,
            @PathVariable Long prontuarioId
    ) throws Exception {
        return ResponseEntity.ok(
                pdfService.writeDocumento(
                    prontuarioService.getProntuarioById(prontuarioId)
                            .orElseThrow(ProntuarioNotFoundException::new),
                    documentoService.provideLayout(tipo)
        ));
    }

    @PostMapping("/{tipo}/{prontuarioId}/{documentoId}")
    public ResponseEntity<Documento> saveDocumentoAssinado(
            @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
            @PathVariable String tipo,
            @PathVariable Long prontuarioId,
            @PathVariable Long documentoId,
            @RequestBody byte[] documento
    ) throws UnsupportedMediaTypeStatusException {
        if(!"application/pdf".equals(
                contentType.toLowerCase()))
            throw new UnsupportedMediaTypeStatusException("É necessário que o Media-Type seja 'application/pdf'");
        Documento doc = prontuarioService.addDocumento(prontuarioId, documentoId, documento, tipo);
        return null;
    }

}

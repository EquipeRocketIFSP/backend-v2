package br.vet.certvet.controllers;

import br.vet.certvet.contracts.apis.ipcBr.IpcResponse;
import br.vet.certvet.exceptions.DocumentoNotFoundException;
import br.vet.certvet.exceptions.DocumentoNotPersistedException;
import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
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

    @GetMapping("/{tipo}/{prontuarioCodigo}/novo")
    public ResponseEntity<byte[]> getDocumentoEmBranco(
            @PathVariable String tipo,
            @PathVariable String prontuarioCodigo
    ) throws ProntuarioNotFoundException,
            DocumentoNotPersistedException,
            OptimisticLockingFailureException,
            IOException {
        var d = prontuarioService.getByCodigo(prontuarioCodigo);
        return ResponseEntity.ok(
                pdfService.writePdfDocumentoEmBranco(
                    prontuarioService.getByCodigo(prontuarioCodigo)
                            .stream().findFirst()
                            .orElseThrow(ProntuarioNotFoundException::new),
                    documentoService.provideLayout(tipo)
        ));
    }


    /**
     *
     * @param tipo Deve ser um dos seguintes: {"sanitario", "obito", "exames", "terapeutico", "retiraCorpo", "cirurgia", "tratamentoClinico", "anestesia", "eutanasia", "retiradaSemAlta", "vacinacao", "doacaoPesquisa"}
     * @param prontuarioCodigo
     * @param documentoId
     * @param documento
     * @return
     * @throws SQLException
     * @throws IOException
     */
    @PostMapping(
            value = "/{tipo}/prontuario/{prontuarioCodigo}/documento/{documentoCodigo}",
            consumes = "application/pdf"
    )
    public ResponseEntity<Documento> saveDocumentoAssinado(
            @PathVariable String tipo,
            @PathVariable String prontuarioCodigo,
            @PathVariable String documentoCodigo,
            @RequestBody byte[] documento
    ) throws SQLException, IOException {
        final Documento queriedDocumento = documentoService.getByCodigo(documentoCodigo)
                .orElseThrow(() -> new DocumentoNotFoundException("Não foi possível identificar o id do documento na base de dados"));
        if(null == queriedDocumento.getMd5())
            throw new DocumentoNotPersistedException("O pdf do documento ainda não existe no repositório");
        IpcResponse ipcResponse = pdfService.getIcpBrValidation(queriedDocumento);

        Documento attachedDocumento = prontuarioService.attachDocumentoAndPdfPersist(prontuarioCodigo, documentoCodigo, documento, tipo);
        return ResponseEntity.ok(attachedDocumento);
    }

}

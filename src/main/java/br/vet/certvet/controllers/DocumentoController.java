package br.vet.certvet.controllers;

import br.vet.certvet.contracts.apis.ipcBr.IpcResponse;
import br.vet.certvet.exceptions.DocumentoNotFoundException;
import br.vet.certvet.exceptions.DocumentoNotPersistedException;
import br.vet.certvet.exceptions.InvalidSignedDocumentoException;
import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
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

    @GetMapping
    public ResponseEntity<List<Documento>> getDocumentosByTipo(
            @RequestParam("tipo") String tipo,
            @RequestParam("prontuario") String prontuarioCodigo
    ){
        List<Documento> documentosProntuario = prontuarioService.getDocumentosFromProntuarioByTipo(prontuarioCodigo, tipo);
        return documentosProntuario.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(documentosProntuario);
    }

    /**
     *
     * @param tipo Deve ser um dos seguintes: {"sanitario", "obito", "exames", "terapeutico", "retiraCorpo", "cirurgia", "tratamentoClinico", "anestesia", "eutanasia", "retiradaSemAlta", "vacinacao", "doacaoPesquisa"}
     * @param prontuarioCodigo Código de um prontuário humanamente legível
     * @return Pdf de arquivo que deverá será assinado digitalmente com uma assinatura reconhecida pelo protocolo ICP-Brasil
     * @throws ProntuarioNotFoundException
     * @throws DocumentoNotPersistedException
     * @throws OptimisticLockingFailureException
     * @throws IOException
     */
    @GetMapping("/novo")
    public ResponseEntity<byte[]> getDocumentoEmBranco(
            @RequestParam("tipo") String tipo,
            @RequestParam("prontuario") String prontuarioCodigo
    ) throws ProntuarioNotFoundException,
            DocumentoNotPersistedException,
            OptimisticLockingFailureException,
            IOException {
        var prontuarioSearch = prontuarioService.getByCodigo(prontuarioCodigo);
        if(prontuarioSearch.isEmpty()) throw new ProntuarioNotFoundException("O prontuário buscado não foi identificado na base de dados");
        Prontuario prontuario = prontuarioSearch.get();
        Documento documento = documentoService.provideLayout(tipo);
        prontuario.getDocumentos().add(documentoService.save(documento));
        return ResponseEntity.ok(pdfService.writePdfDocumentoEmBranco(
                prontuario,
                documentoService.provideLayout(tipo)
        ));
    }


    /**
     * @param prontuarioCodigo Codigo legível por humano do Prontuario gerado
     * @param documentoCodigo Codigo legível por humano do Documento associado ao Prontuario gerado
     * @param documentoPdf Documento PDF assinado
     * @return Lista de documentos associados com o prontuário em questão
     * @throws SQLException
     * @throws IOException
     */
    @PostMapping(
//            consumes = MediaType.APPLICATION_PDF_VALUE //"application/pdf"
    )
    public ResponseEntity<List<Documento>> saveDocumentoAssinado(
            @RequestParam("prontuario") String prontuarioCodigo,
            @RequestParam("documento") String documentoCodigo,
            @RequestBody byte[] documentoPdf
    ) throws IOException, SQLException {
        final Documento queriedDocumento = documentoService.getByCodigo(documentoCodigo)
                .orElseThrow(() -> new DocumentoNotFoundException("Não foi possível identificar o id do documento na base de dados"));
//        if(null == queriedDocumento.getMd5())
//            throw new DocumentoNotPersistedException("O pdf do documento ainda não existe no repositório");
        IpcResponse ipcResponse = pdfService.getIcpBrValidation(queriedDocumento);
        if(!ipcResponse.isValidDocument()) throw new InvalidSignedDocumentoException("O documento não pôde ser confirmado pelo IPC-BR");
        Prontuario prontuario = prontuarioService.attachDocumentoAndPdfPersist(prontuarioCodigo, documentoCodigo, documentoPdf);
        return ResponseEntity.ok(prontuario.getDocumentos());
//        return ResponseEntity.ok(attachedDocumento);
    }

}

package br.vet.certvet.controllers;

import br.vet.certvet.contracts.apis.ipcBr.IcpResponse;
import br.vet.certvet.dto.requests.DocumentoPdfDto;
import br.vet.certvet.dto.responses.DocumentoResponse;
import br.vet.certvet.exceptions.*;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.repositories.AnimalRepository;
import br.vet.certvet.repositories.DocumentoRepository;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import br.vet.certvet.services.implementation.PdfFromHtmlPdfServiceImpl;
import br.vet.certvet.services.implementation.ProntuarioServiceImpl;
import br.vet.certvet.services.implementation.S3BucketServiceRepository;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/api/documento")
@SecurityRequirement(name = "bearer-key")
public class DocumentoController extends BaseController {

    @Autowired
    private ProntuarioService prontuarioService;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private PdfService pdfService;

    private Prontuario findProntuarioEClinica(String auth, String prontuarioCodigo) {
        return prontuarioService.findByCodigo(prontuarioCodigo)
                .filter(p -> p.getClinica().getId().equals(getClinicaIdFromRequester(auth)))
                .orElseThrow(()->new ProntuarioNotFoundException("O prontuário buscado não foi identificado na base de dados"));
    }

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
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam("tipo") String tipo,
            @RequestParam("prontuario") String prontuarioCodigo,
            @RequestBody DocumentoPdfDto documentoPdfDto
    ) throws ProntuarioNotFoundException,
            DocumentoNotPersistedException,
            OptimisticLockingFailureException,
            IOException {
        Prontuario prontuario = findProntuarioEClinica(auth, prontuarioCodigo);
        Documento documento = documentoService.save(documentoPdfDto.toDocumento(prontuario, tipo));
        return ResponseEntity.ok(
                pdfService.writePdfDocumentoEmBranco(
                        documento, documentoService.provideLayout(tipo)
                )
        );
    }


    /**
     * @param prontuarioCodigo Codigo legível por humano do Prontuario gerado
     * @param documentoCodigo Codigo legível por humano do Documento associado ao Prontuario gerado
     * @param documentoPdf Documento PDF assinado
     * @return Lista de documentos associados com o prontuário em questão
     * @throws SQLException
     * @throws IOException
     */
    @PostMapping(consumes = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<DocumentoResponse> saveDocumentoAssinado(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam("prontuario") String prontuarioCodigo,
            @RequestParam("documento") String documentoCodigo,
            @RequestParam("versao") Integer versao,
            @RequestBody byte[] documentoPdf
    ) throws IOException, SQLException {
        final int version = null == versao ? -1 : versao;
        Prontuario prontuario = findProntuarioEClinica(auth, prontuarioCodigo);
        Documento documento = prontuario.getDocumentos()
                .stream()
                .filter(p -> p.getCodigo().equals(documentoCodigo))
                .findFirst()
                .orElseThrow(() -> new DocumentoNotFoundException("Não foi possível identificar o id do documento na base de dados"));

        // Disponibiliza o arquivo na AWS para que possa ser validado pelo serviço do ICP-BR
        ObjectMetadata awsResponse = pdfService.saveDocumentoPdfInBucket(documento, version, documentoPdf);
        // Obtém dados convecionados para salvar o arquivo
        final String bucket = S3BucketServiceRepository.getConventionedBucketName(documento.getClinica().getCnpj());
        final String fileName = ProntuarioServiceImpl.writeNomeArquivo(documento, version);
        IcpResponse icpResponse = pdfService.getIcpBrValidation(bucket, fileName);
        if(!icpResponse.isValidDocument()) throw new InvalidSignedDocumentoException("O documento não pôde ser confirmado pelo ICP-BR");
        Documento attachedDocumento = prontuarioService.attachDocumentoAndPdfPersist(
                documento.assinadores(PdfFromHtmlPdfServiceImpl.assinadoresPresentesSistema(icpResponse)),
                awsResponse,
                version
        );
        return ResponseEntity.created(URI.create("/api/documento/" + documentoCodigo))
                .body(new DocumentoResponse(attachedDocumento));
    }
}

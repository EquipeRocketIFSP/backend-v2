package br.vet.certvet.controllers;

import br.vet.certvet.contracts.apis.ipcBr.IcpResponse;
import br.vet.certvet.dto.responses.DocumentoResponse;
import br.vet.certvet.exceptions.*;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.AnimalRepository;
import br.vet.certvet.repositories.UsuarioRepository;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/documento")
@SecurityRequirement(name = "bearer-key")
public class DocumentoController extends BaseController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AnimalRepository animalRepository;

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

    private List<Usuario> validaAssinadores(IcpResponse icpResponse) {
        List<Usuario> assinadores = new ArrayList<>();
        List<String> naoCadastrados = new ArrayList<>();
        for(String key : icpResponse.getSigners().keySet()){
            var a = usuarioRepository.findByCpf(icpResponse.getSigners().get(key).signerCpf());
            if(a.isEmpty()) naoCadastrados.add(icpResponse.getSigners().get(key).signerCpf());
            else assinadores.add(a.get());
        }
        if(!naoCadastrados.isEmpty()) throw new AssinadorNaoCadastradoException("Os CPF assinadores não estão cadastrados: " + naoCadastrados + ". Verifique se todos os assinadores estão cadastrados e salve o arquivo novamente.");
        return assinadores;
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
            @RequestParam("prontuario") String prontuarioCodigo
    ) throws ProntuarioNotFoundException,
            DocumentoNotPersistedException,
            OptimisticLockingFailureException,
            IOException {
        Prontuario prontuario = findProntuarioEClinica(auth, prontuarioCodigo);
        return ResponseEntity.ok(
                pdfService.writePdfDocumentoEmBranco(
                        prontuario, documentoService.provideLayout(tipo)
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
            @RequestBody byte[] documentoPdf
    ) throws IOException, SQLException {
        Prontuario prontuario = findProntuarioEClinica(auth, prontuarioCodigo);
        Documento documento = prontuario.getDocumentos()
                .stream()
                .filter(p -> p.getCodigo().equals(documentoCodigo))
                .findFirst()
                .orElseThrow(() -> new DocumentoNotFoundException("Não foi possível identificar o id do documento na base de dados"));
        var awsResponse = pdfService.savePdfInBucket(documento, documentoPdf);
        IcpResponse icpResponse = pdfService.getIcpBrValidation(documento);
        if(!icpResponse.isValidDocument()) throw new InvalidSignedDocumentoException("O documento não pôde ser confirmado pelo ICP-BR");
        Documento attachedDocumento = prontuarioService.attachDocumentoAndPdfPersist(
                documento.assinadores(validaAssinadores(icpResponse)), awsResponse);
        return ResponseEntity.created(URI.create("/api/documento/" + documentoCodigo))
                .body(new DocumentoResponse(attachedDocumento));
    }
}

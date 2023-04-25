package br.vet.certvet.controllers;

import br.vet.certvet.contracts.apis.ipcBr.IpcResponse;
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

    @GetMapping("/{tipo}/{prontuarioId}/novo")
    public ResponseEntity<byte[]> getDocumentoEmBranco(
            @PathVariable String tipo,
            @PathVariable Long prontuarioId
    ) throws
            ProntuarioNotFoundException,
            DocumentoNotPersistedException,
            OptimisticLockingFailureException,
            IOException {
        return ResponseEntity.ok(
                pdfService.writeDocumento(
                    prontuarioService.getProntuarioById(prontuarioId)
                            .orElseThrow(ProntuarioNotFoundException::new),
                    documentoService.provideLayout(tipo)
        ));
    }


    @PostMapping(
            value = "/{tipo}/{prontuarioId}/{documentoId}",
            consumes = "application/pdf"
    )
    public ResponseEntity<Documento> saveDocumentoAssinado(
//            @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
            @PathVariable String tipo,
            @PathVariable Long prontuarioId,
            @PathVariable Long documentoId,
            @RequestBody byte[] documento
    ) throws SQLException {
//        if(!"application/pdf".equals(
//                contentType.toLowerCase()))
//            throw new UnsupportedMediaTypeStatusException("É necessário que o Media-Type seja 'application/pdf'");
        Documento doc = prontuarioService.attachDocumento(prontuarioId, documentoId, documento, tipo);
        return null;
    }

    @GetMapping
    public ResponseEntity<IpcResponse> run() throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(executeGet());
    }
//
//    private IpcResponse test() throws IOException {
//        IpcResponse r = IpcResponse.builder()
//                .healthInfo(IpcHealthInfo.builder()
//                        .dispensationReceipt(IpcHealthInfoDispensationReceipt.builder().build())
//                        .dispensed(Boolean.FALSE)
//                        .documentHash("documentHash")
//                        .documentStatus("documentStatus")
//                        .documentType("documentType")
//                        .errorCodes(List.of(
//                                        IpcHealthInfoErrorCodes.builder().build()
//                                ))
//                        .form(IpcHealthInfoForm.builder().build())
//                        .pharmacist(IpcHealthInfoPharmacist.builder().build())
//                        .prescriber(IpcHealthInfoPrescriber.builder().build())
//                        .software(IpcHealthInfoSoftware.builder().build())
//                        .validDocument(Boolean.FALSE)
//                        .build())
//                .receipt("receipt")
//                .report(IpcReport.builder()
//                        .date(IpcReportDate.builder().build())
//                        .extendedReport(Boolean.FALSE)
//                        .generalStatus("generalStatus")
//                        .initialReport(IcpReportInitialReport.builder().build())
//                        .number(0)
//                        .onlyVerifyAnchored(Boolean.FALSE)
//                        .signatures(IcpReportSignatures.builder()
//                                .signature(List.of(
//                                        IpcReportSignature.builder()
//                                                .signaturePolicy("signaturePolicy")
//                                                .attributes(IpcAttributes.builder().build())
//                                                .attributeValid(Boolean.FALSE)
//                                                .certification(IpcCertification.builder().build())
//                                                .paRules(IpcPaRules.builder().build())
//                                                .errorMessages("errorMessages")
//                                                .containsMandatedCertificates(Boolean.FALSE)
//                                                .hasInvalidUpdates(Boolean.FALSE)
//                                                .integrity(IpcIntegrity.builder().build())
//                                                .signatureType("signatureType")
//                                                .signingTime("SigningTime")
//                                                .warningMessages("warningMessages")
//                                                .build()
//                                ))
//                                .build())
//                        .software(IcpReportSoftware.builder().build())
//                        .build())
//                .build();
//        ObjectMapper mapper = new ObjectMapper();
//
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        File f = new File("res/user.json");
//        mapper.writeValue(f, r);
//        return (IpcResponse) mapper.readValue(Files.readString(Path.of(new File("res/user.json").toURI())), IpcResponse.class);
//    }
    private IpcResponse executeGet() {
//    private String executeGet(final String https_url, final String proxyName, final int port) {
        String ret = "https://validar.iti.gov.br/validar?signature_files=https://certvet-signed.s3.us-east-1.amazonaws.com/test_documento_sanitario_assinado_assinado.pdf";
        URL url;
        String response = null;
        IpcResponse ir = null;
        try {
            url = new URL(ret);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setRequestProperty("Content-Type", "*/*");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setDoOutput(true);
            int status = con.getResponseCode();
            Reader streamReader = null;
            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }
            String inputLine;
            StringBuffer content = new StringBuffer();
            try(BufferedReader in = new BufferedReader(streamReader)) {
                while ((inputLine = in.readLine()) != null)
                    content.append(inputLine);
            }
            response = content.toString();
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            ir = (IpcResponse)mapper.readValue(response, IpcResponse.class);
            con.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ir;
    }

}

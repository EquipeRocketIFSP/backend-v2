package br.vet.certvet.controllers;

import br.vet.certvet.exceptions.DocumentoNotPersistedException;
import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> run(){
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(executeGet());
    }
    private String executeGet() {
//    private String executeGet(final String https_url, final String proxyName, final int port) {
        String ret = "https://validar.iti.gov.br/validar?signature_files=https://certvet-signed.s3.us-east-1.amazonaws.com/test_documento_sanitario_assinado_assinado.pdf";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");
        URL url;
        String response = null;
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
//            try(DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
//                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
//                out.flush();
//            }
            con.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
    private static class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }
    }

}

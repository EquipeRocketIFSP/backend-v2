package br.vet.certvet.services.implementation;

import br.vet.certvet.contracts.apis.ipcBr.IpcResponse;
import br.vet.certvet.exceptions.DocumentoNotPersistedException;
import br.vet.certvet.exceptions.PdfNaoReconhecidoException;
import br.vet.certvet.helpers.Https;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.DocumentoRepository;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.services.PdfService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class PdfFromHtmlPdfServiceImpl implements PdfService {

    final static String ERRO = "{\"Error\":\"E022: Não foi possível baixar o arquivo da URL fornecida\"}";

    @Value("${app.default.pdf.password}")
    private String OWNER_PASSWORD;
    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    public byte[] writeProntuario(Prontuario prontuario) throws Exception {

        String from = "src/main/resources/documents/prontuario/ProntuarioLayout.html";
        String fileName = prontuario.getCodigo() + ".pdf";
        String layout = Files.readString(Path.of(from));
        Map<String, String> parameters = Map.of(
                "animal.nome", prontuario.getAnimal().getNome(),
                "veterinario.nome", prontuario.getVeterinario().getNome(),
                "veterinario.crmv", prontuario.getVeterinario().getRegistroCRMV(),
                "clinica.razaoSocial", prontuario.getClinica().getRazaoSocial(),
                "clinica.telefone", prontuario.getClinica().getTelefone(),
                "prontuario.codigo", prontuario.getCodigo()
        );
        return transformTxtToXmlToPdf(parameters, layout);
    }

    @Override
    public byte[] writePdfDocumentoEmBranco(
            Prontuario prontuario,
            Documento documentoTipo
    ) throws
            DocumentoNotPersistedException,
            OptimisticLockingFailureException,
            IOException {
        final String from = "src/main/resources/documents/consentimento/ConsentimentoLayoutV2.html";
        final String layout = Files.readString(Path.of(from));

        Map<String, String> parameters = getDivsToBeLoaded(documentoTipo);
        documentoTipo.setCodigo(LocalDateTime.now());
        documentoRepository.save(documentoTipo);
        final String htmlBase = new StringSubstitutor(parameters).replace(layout);

        parameters = getFieldsToBeLoaded(prontuario);
        return transformTxtToXmlToPdf(parameters, htmlBase);
    }

    private byte[] transformTxtToXmlToPdf(Map<String, String> parameters, String htmlBase) throws IOException {
        String result = new StringSubstitutor(parameters).replace(htmlBase);
        Document document = Jsoup.parse(result, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return generatePdfFromHtml(document);
    }

    private static ImmutableMap<String, String> getFieldsToBeLoaded(Prontuario prontuario) {
        return ImmutableMap.<String, String>builder()
                .put("animal.nome", prontuario.getAnimal().getNome())
                .put("veterinario.nome", prontuario.getVeterinario().getNome())
                .put("veterinario.crmv", prontuario.getVeterinario().getRegistroCRMV())
                .put("clinica.razaoSocial", prontuario.getClinica().getRazaoSocial())
                .put("clinica.telefone", prontuario.getClinica().getTelefone())
                .put("prontuario.codigo", prontuario.getCodigo())
                .put("animal.especie", prontuario.getAnimal().getEspecie())
                .put("animal.raca", prontuario.getAnimal().getRaca())
                .put("animal.sexo", prontuario.getAnimal().getSexo().name().toLowerCase())
                .put("animal.idade", String.valueOf(prontuario.getAnimal().getIdade()))
                .put("animal.pelagem", prontuario.getAnimal().getPelagem())
                .put("documento.observacaoVet", "observacaoVet") //TODO: Substituir pela observacao do documento
                .put("documento.observacaoTutor", "observacaoTutor") //TODO: Substituir pela observacao do documento
                .put("documento.causaMortis", "causaMortis") //TODO: Substituir pela observacao do documento
                .put("documento.orientaDestinoCorpo", "orientaDestinoCorpo") //TODO: Substituir pela observacao do documento
                .put("tutor.nome", prontuario.getTutor().getNome())
                .put("tutor.cpf", prontuario.getTutor().getCpf())
                .put("tutor.endereco", prontuario.getTutor().getEnderecoCompleto())
                .put("documento.outrasObservacoes", "outrasObservacoes") //TODO: Substituir pela observacao do documento
                .put("cidade", prontuario.getClinica().getCidade())
                .put("data.dia", String.valueOf(prontuario.getDataAtendimento().getDayOfMonth()))
                .put("data.mes", prontuario.getMonthAtendimento())
                .put("data.ano", String.valueOf(prontuario.getDataAtendimento().getYear()))
                .put("prontuario.obito.local", "prontuario.obito.local") //TODO: Substituir pela observacao do documento
                .put("prontuario.obito.horas", "prontuario.obito.horas") //TODO: Substituir pela observacao do documento
                .put("prontuario.obito.data", "prontuario.obito.data") //TODO: Substituir pela observacao do documento
                .put("prontuario.obito.causa", "prontuario.obito.causa")  //TODO: Substituir pela observacao do documento
                .put("prontuario.exames", String.valueOf(prontuario.getExames()))
                .put("prontuario.terapias", "prontuario.terapia") //TODO: Identificar como ficarão registradas as terapias
                .put("prontuario.cirurgia", String.valueOf(prontuario.getCirurgia()))
                .put("prontuario.anestesia", "prontuario.anestesia") //TODO: Identificar como ficarão registradas as anestesias
                .build();
    }

    private static ImmutableMap<String, String> getDivsToBeLoaded(Documento documento) {
        return ImmutableMap.<String, String>builder()
                .put("documento.titulo", documento.getTitulo())
                .put("documento.declara_consentimento", documento.getDeclaraConsentimento())
                .put("documento.declara_ciencia_riscos", documento.getDeclaraCienciaRiscos() == null
                        ? ""
                        : documento.getDeclaraCienciaRiscos())
                .put("documento.observacoes_veterinario", documento.getObservacoesVeterinario() == null
                        ? ""
                        : documento.getObservacoesVeterinario())
                .put("documento.observacoes_responsavel", documento.getObservacoesResponsavel() == null
                        ? ""
                        : documento.getObservacoesResponsavel())
                .put("documento.causaMortis", documento.getCausaMortis() == null
                        ? ""
                        : documento.getCausaMortis())
                .put("documento.orientaDestinoCorpo", documento.getOrientaDestinoCorpo() == null
                        ? ""
                        : documento.getOrientaDestinoCorpo())
                .put("documento.outrasObservacoes", documento.getOutrasObservacoes() == null
                        ? ""
                        : documento.getOutrasObservacoes())
                .put("documento.assinatura_responsavel", documento.getAssinaturaResponsavel() == null
                        ? ""
                        : documento.getAssinaturaResponsavel())
                .put("documento.assinatura_vet", documento.getAssinaturaVet() == null
                        ? ""
                        : documento.getAssinaturaVet())
                .put("documento.explica_duas_vias", documento.getExplicaDuasVias() == null
                        ? ""
                        : documento.getExplicaDuasVias())
                .build();
    }

    private static byte[] generatePdfFromHtml(Document document) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            sharedContext.setReplacedElementFactory(new MediaReplacedElementFactory(sharedContext.getReplacedElementFactory()));
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }

    @Override
    public byte[] retrieveFromRepository(Prontuario prontuario) throws IOException {
//        PdfReader reader = new PdfReader();
//        PdfStamper //
        return new byte[0];
    }

    @Override
    public byte[] setProtection(byte[] documento, Prontuario prontuario) throws IOException {
        try(PdfReader reader = new PdfReader(documento)){
            try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                PdfStamper stamper = new PdfStamper(reader, out);
                try {
                    stamper.setEncryption(
                            getTutorPass(prontuario).getBytes(),
                            OWNER_PASSWORD.getBytes(),
                            PdfWriter.ALLOW_PRINTING,
                            PdfWriter.ENCRYPTION_AES_128
                    );
                    return out.toByteArray();
                } finally {
                    stamper.close();
                }
            }
        }
    }

    @Override
    public IpcResponse getIcpBrValidation(Documento documento) throws IOException, PdfNaoReconhecidoException {
//        final String requestUrl = new StringBuilder().append("https://validar.iti.gov.br/validar?signature_files=https://")
//                .append(S3BucketServiceRepository.getConventionedBucketName(documento.getClinica().getCnpj())) // S3 folder
//                .append(".s3.us-east-1.amazonaws.com/")
//                .append(documento.getName()) // S3 fileName
//                .toString();
        String requestUrl = "https://validar.iti.gov.br/validar?signature_files=https://certvet-signed.s3.us-east-1.amazonaws.com/test_documento_sanitario_assinado_assinado.pdf";
        final String json = Https.get(requestUrl, Map.of("Content-Type", "*/*", "Cache-Control", "no-cache"));
        if(json.equals(ERRO)) throw new PdfNaoReconhecidoException("O documento pdf não foi identificado no servidor");
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            return (IpcResponse) mapper.readValue(json, IpcResponse.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            throw e;
        }
//        throw new ErroMapeamentoRespostaException("Não foi possível processar o documento com o ICP-BR.");
    }

    private static String getTutorPass(Prontuario prontuario) {
        return prontuario.getTutor()
                .getCpf()
                .replace(".", "")
                .substring(5, 9);
    }
}

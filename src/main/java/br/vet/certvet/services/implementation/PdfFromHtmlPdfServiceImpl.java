package br.vet.certvet.services.implementation;

import br.vet.certvet.contracts.apis.ipc_br.IcpResponse;
import br.vet.certvet.exceptions.*;
import br.vet.certvet.helpers.Https;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.models.especializacoes.Doc;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.DocumentoRepository;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.repositories.UsuarioRepository;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.implementation.helper.ProntuarioPdfHelper;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
@Slf4j
public class PdfFromHtmlPdfServiceImpl implements PdfService {

    private static final String ERRO = "{\"Error\":\"E022: Não foi possível baixar o arquivo da URL fornecida\"}";

    @Value("${app.default.pdf.password}")
    private String ownerPassword;
    private final DocumentoRepository documentoRepository;

    private final PdfRepository pdfRepository;

    private final ClinicaRepository clinicaRepository;

    private final UsuarioRepository usuarioRepository;

    public PdfFromHtmlPdfServiceImpl(DocumentoRepository documentoRepository, PdfRepository pdfRepository, ClinicaRepository clinicaRepository, UsuarioRepository usuarioRepository) {
        this.documentoRepository = documentoRepository;
        this.pdfRepository = pdfRepository;
        this.clinicaRepository = clinicaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public byte[] writeProntuario(Prontuario prontuario) {
        final String layoutFile = "src/main/resources/documents/prontuario/ProntuarioLayout.html";
        String layout;
        try{
            layout = Files.readString(Path.of(layoutFile));
        }catch (IOException e){
            log.error(e.getLocalizedMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new EscritaProntuarioPdfException("Erro ao realizar ao ler o documento de referência para geração do pdf de Prontuário.");
        }
        layout = ProntuarioPdfHelper.fillLayoutFieldsForProntuario(prontuario, layout);
        return transformTxtToXmlToPdf(layout);
    }

    @Override
    public byte[] writePdfDocumentoEmBranco(
            Prontuario prontuario,
            Doc documentoTipo
    ) throws
            DocumentoNotPersistedException,
            OptimisticLockingFailureException {
        final String from = "src/main/resources/documents/consentimento/ConsentimentoLayoutV2.html";

        String layout;
        try{
            layout = Files.readString(Path.of(from));
        }catch (IOException e){
            log.error(e.getLocalizedMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new EscritaDocumentoPdfException("Erro ao realizar ao ler o documento de referência para geração do pdf de Documento de Autorização/Consentimento.");
        }

        Documento documento = documentoRepository.save(documentoTipo.getDocumento().fromProntuario(prontuario));
//        documento.prontuario(prontuario);

        layout = ProntuarioPdfHelper.replaceWithDivsForDocumento(documentoTipo, layout);
        layout = ProntuarioPdfHelper.fillLayoutFieldsForDocumento(documento, layout);
        return transformTxtToXmlToPdf(layout);
    }

    private byte[] transformTxtToXmlToPdf(String htmlBase) {
        Document document = Jsoup.parse(htmlBase, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
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
        }catch (IOException e){
            log.error(e.getLocalizedMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new RendezizacaoPDFException("Falha ao renderizar o PDF. Causa: " + e.getCause());
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
                            ownerPassword.getBytes(),
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

    /**
     * Utiliza o serviço do iti para validar assinaturas.
     * ex.: https://validar.iti.gov.br/validar?signature_files=https://certvet-signed.s3.us-east-1.amazonaws.com/test_documento_sanitario_assinado_assinado.pdf
     * @param bucket
     * @param fileName
     * @return
     * @throws IOException
     * @throws PdfNaoReconhecidoException
     */
    @Override
    public IcpResponse getIcpBrValidation(final String bucket, final String fileName) throws PdfNaoReconhecidoException {
        final String requestUrl = getSignValidationUrl(bucket, fileName);
        String json = ERRO;
        try {
            if(Boolean.TRUE.equals(pdfRepository.setPublicFileReadingPermission(bucket, true))) {// Libera objeto para que seja acessado publicamente na AWS
                json = Https.get(requestUrl, Map.of("Content-Type", "*/*", "Cache-Control", "no-cache"));
            } else {
                throw new AwsPermissionDeniedException("A requisição para mudança de perfil de acesso foi negada pelo provedor");
            }
        } catch (IOException e){
            log.error("Erro ao realizar liberação do arquivo para ser acessado publicamente");
            log.error(e.getLocalizedMessage());
            throw new ProcessamentoIcpBrJsonRequestException("O sistema Icp-BR não devolveu um retorno que pudesse ser processado.");
        } finally {
            pdfRepository.setPublicFileReadingPermission(bucket, false); // Sempre trava após a conexão com o serviço de assinatura
        }
        if(ERRO.equals(json)) throw new PdfNaoReconhecidoException("O documento pdf não foi identificado no servidor pelo validador Icp-BR");
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            return mapper.readValue(json, IcpResponse.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            throw new ProcessamentoIcpBrJsonResponseException("Ocorreu um erro ao realizar o processamento da resposta do serviço ICP-BR");
        }
    }

    @Override
    public ObjectMetadata saveDocumentoPdfInBucket(final Documento documento, final int version, final byte[] documentoPdf) {
        return pdfRepository.putObject(
                documento.getProntuario().getClinica().getCnpj(),
                ProntuarioServiceImpl.writeNomeArquivo(documento, version),// fileName.substring(fileName.indexOf("/")+1),
                documentoPdf
        );
    }

    @Override
    public Optional<byte[]> getPrescricaoPdf(final Prontuario prontuario, int version) {
        final String cnpj = S3BucketServiceRepository.getConventionedBucketName(
                prontuario.getClinica()
                        .getCnpj());
        final String keyName = prontuario.getPrescricoes(version)
                .stream()
                .findFirst()
                .orElseThrow()
                .getCodigo() +
                "-v" + version;
        try {
            return pdfRepository.retrieveObject(cnpj, keyName);
        } catch (IOException e){
            log.error(e.getLocalizedMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<byte[]> writePrescricao(Prontuario prontuario, int version) {
        final String layoutFile = "src/main/resources/documents/prontuario/PrescricaoLayout.html";
        String layout;
        try{
            layout = Files.readString(Path.of(layoutFile));
        }catch (IOException e){
            log.error(e.getLocalizedMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new EscritaPrescricaoPdfException("Erro ao ler o documento de referência para geração do pdf da prescrição.");
        }
        layout = ProntuarioPdfHelper.replaceWithDivsForPrescricao(layout, prontuario.getPrescricoes(version));
        layout = ProntuarioPdfHelper.fillLayoutFieldsForPrescricao(layout, prontuario);
        return Optional.of(transformTxtToXmlToPdf(layout));
    }

    @Override
    public ObjectMetadata savePrescricaoPdfInBucket(final Prontuario prontuario, final int version, final byte[] medicacaoPrescritaPdf) {
        return pdfRepository.putObject(
                prontuario.getClinica().getCnpj(),
                ProntuarioServiceImpl.writeNomeArquivoPrescricao(prontuario, version),
                medicacaoPrescritaPdf
        );
    }

    @Override
    public List<Usuario> assinadoresPresentesSistema(IcpResponse icpResponse) {
        List<Usuario> assinadores = new ArrayList<>();
        List<String> naoCadastrados = new ArrayList<>();
        for(String key : icpResponse.getSigners().keySet()){
            Optional<Usuario> a = usuarioRepository.findByCpf(icpResponse.getSigners().get(key).signerCpf());
            if(a.isEmpty()) naoCadastrados.add(icpResponse.getSigners().get(key).signerCpf());
            else assinadores.add(a.get());
        }
        if(!naoCadastrados.isEmpty()) throw new AssinadorNaoCadastradoException("Os CPF assinadores não estão cadastrados: " + naoCadastrados + ". Verifique se todos os assinadores estão cadastrados e salve o arquivo novamente.");
        return assinadores;
    }

    private static String getSignValidationUrl(String bucket, String fileName) {
        return new StringBuilder().append("https://validar.iti.gov.br/validar?signature_files=https://")
                .append("s3.sa-east-1.amazonaws.com/")
                .append(bucket) // S3 folder
                .append("/")
                .append(fileName) // S3 fileName
                .toString();
    }

    private static String getTutorPass(Prontuario prontuario) {
        return prontuario.getTutor()
                .getCpf()
                .replace(".", "")
                .substring(5, 9);
    }
}

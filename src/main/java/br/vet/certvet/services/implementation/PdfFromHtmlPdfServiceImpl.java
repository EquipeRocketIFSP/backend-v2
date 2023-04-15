package br.vet.certvet.services.implementation;

import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.services.PdfService;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.text.StringSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class PdfFromHtmlPdfServiceImpl implements PdfService {

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    public byte[] termoAutorizacaoProcedimentoCirurgico(String estabelecimento, String procedimento, String cidade, Animal animal, Usuario veterinario, Usuario tutor) throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] writeProntuario(Prontuario prontuario) throws Exception {

        String from = "src/main/resources/documents/prontuario/ProntuarioLayout.html";
        String fileName = "res/" + prontuario.getCodigo() + ".pdf";
        String layout = Files.readString(Path.of(from));
        Map<String, String> parameters = Map.of(
                "animal.nome", prontuario.getAnimal().getNome(),
                "veterinario.nome", prontuario.getVeterinario().getNome(),
                "veterinario.crmv", prontuario.getVeterinario().getRegistroCRMV(),
                "clinica.razaoSocial", prontuario.getClinica().getRazaoSocial(),
                "clinica.telefone", prontuario.getClinica().getTelefone(),
                "prontuario.codigo", prontuario.getCodigo()
        );
        return transformTxtToXmlToPdf(fileName, parameters, layout);
    }

    @Override
    public byte[] writeDocumento(Prontuario prontuario, Documento documentoTipo) throws Exception {
        final String fileName = "res/" + prontuario.getCodigo() + ".pdf";
        final String from = "src/main/resources/documents/consentimento/ConsentimentoLayoutV2.html";
        final String layout = Files.readString(Path.of(from));

        Map<String, String> parameters = getDivsToBeLoaded(documentoTipo);
        final String htmlBase = new StringSubstitutor(parameters).replace(layout);

        parameters = getFieldsToBeLoaded(prontuario);
        return transformTxtToXmlToPdf(fileName, parameters, htmlBase);
    }

    private byte[] transformTxtToXmlToPdf(String fileName, Map<String, String> parameters, String htmlBase) throws IOException {
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
        return new byte[0];
    }
}

package br.vet.certvet.services.implementation;

import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.services.PdfService;
import org.apache.commons.text.StringSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        String result = new StringSubstitutor(parameters).replace(layout);
        Document document = Jsoup.parse(result, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        generatePdfFromHtml(document, new File(fileName));
        Path of = Path.of(fileName);
        try {
            return Files.readAllBytes(of);
        } finally {
            Files.deleteIfExists(of);
        }
    }

    @Override
    public byte[] writeDocumento(Prontuario prontuario, String documentoTipo) throws Exception {

        String from = "src/main/resources/documents/consentimento/ConsentimentoLayout.html";
        String fileName = "res/" + prontuario.getCodigo() + ".pdf";
        String layout = Files.readString(Path.of(from));

        String[] keyValue = {
                "animal.nome", prontuario.getAnimal().getNome(),
                "veterinario.nome", prontuario.getVeterinario().getNome(),
                "veterinario.crmv", prontuario.getVeterinario().getRegistroCRMV(),
                "clinica.razaoSocial", prontuario.getClinica().getRazaoSocial(),
                "clinica.telefone", prontuario.getClinica().getTelefone(),
                "prontuario.codigo", prontuario.getCodigo(),
                "animal.especie", prontuario.getAnimal().getEspecie(),
                "animal.raca", prontuario.getAnimal().getRaca(),
                "animal.sexo", prontuario.getAnimal().getSexo().name().toLowerCase(),
                "animal.idade", String.valueOf(prontuario.getAnimal().getIdade()),
                "animal.pelagem", prontuario.getAnimal().getPelagem(),
                "documento.observacaoVet", "observacaoVet", //TODO: Substituir pela observacao do documento
                "documento.observacaoTutor", "observacaoTutor", //TODO: Substituir pela observacao do documento
                "documento.causaMortis", "causaMortis", //TODO: Substituir pela observacao do documento
                "documento.orientaDestinoCorpo", "orientaDestinoCorpo", //TODO: Substituir pela observacao do documento
                "tutor.nome", prontuario.getTutor().getNome(),
                "tutor.cpf", prontuario.getTutor().getCpf(),
                "tutor.endereco", prontuario.getTutor().getEnderecoCompleto(),
                "documento.outrasObservacoes", "outrasObservacoes", //TODO: Substituir pela observacao do documento
                "prontuario.cidade", prontuario.getClinica().getCidade(),
                "prontuario.dia", String.valueOf(prontuario.getDataAtendimento().getDayOfMonth()),
                "prontuario.mes", prontuario.getDataAtendimento().getMonth().name(),
                "prontuario.ano", String.valueOf(prontuario.getDataAtendimento().getYear())
        };

        Map<String, String> parameters = new HashMap<>();
        for(int i = 0; i < keyValue.length; i = i+2){
            parameters.put(keyValue[i], keyValue[i+1]);
        }
        String result = new StringSubstitutor(parameters).replace(layout);
        Document document = Jsoup.parse(result, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        generatePdfFromHtml(document, new File(fileName));
        Path of = Path.of(fileName);
        try {
            return Files.readAllBytes(of);
        } finally {
            Files.deleteIfExists(of);
        }
    }

    private static void generatePdfFromHtml(Document document, File outputPdf) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(outputPdf)) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            sharedContext.setReplacedElementFactory(new MediaReplacedElementFactory(sharedContext.getReplacedElementFactory()));
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(outputStream);
        }
    }

    @Override
    public byte[] retrieveFromRepository(Prontuario prontuario) throws IOException {
        return new byte[0];
    }
}

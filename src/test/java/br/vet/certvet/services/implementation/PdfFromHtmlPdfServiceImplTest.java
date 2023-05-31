package br.vet.certvet.services.implementation;

import br.vet.certvet.enums.SexoAnimal;
import br.vet.certvet.models.*;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.DocumentoRepository;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.DocumentoService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
//@EnableConfigurationProperties
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@ActiveProfiles("test")
public class PdfFromHtmlPdfServiceImplTest {

    public static final Date DATE = new Date();
    public static final Locale L = new Locale("pt", "BR");

//    @Mock
    private ProntuarioRepository prontuarioRepository = mock(ProntuarioRepository.class);

//    @Mock
    private ClinicaRepository clinicaRepository = mock(ClinicaRepository.class);

//    @Mock
    private PdfRepository pdfRepository = mock(PdfRepository.class);

//    @Mock
    private DocumentoService documentoService = mock(DocumentoService.class);

//    @Mock
    private DocumentoRepository documentoRepository = mock(DocumentoRepository.class);

//    @InjectMocks
//    @Qualifier("pdfFromHtmlPdfServiceImpl")
    private PdfFromHtmlPdfServiceImpl service;

    @BeforeEach
    void setUp(){
        service = new PdfFromHtmlPdfServiceImpl(documentoRepository, pdfRepository, clinicaRepository);
    }

    @AfterEach
    void tearDown(){
        service = null;
    }

    private Prontuario getProntuarioInstance(){
        Clinica clinica = Clinica.builder()
                .cidade("Cidade das Abelhas")
                .razaoSocial("Clinica vet")
                .telefone("(12) 3456-7890")
                .build();
        Usuario tutor = Usuario.builder()
                .nome("Caio Felipe Pires")
                .cpf("175.578.151-22")
                .rg("13.123.399-3")
                .cep("66053-140")
                .logradouro("Praça Magalhães")
                .numero("242")
                .bairro("Reduto")
                .cidade("Belém")
                .estado("PA")
                .telefone("(91) 2792-2741")
                .celular("(91) 99850-3799")
                .email("caio_pires@lanchesdahora.com.br")
                .username("caio_pires@lanchesdahora.com.br")
                .password("6EzlRrYEzy")
                .clinica(clinica)
                .build();
        Usuario veterinario = Usuario.builder()
                .nome("Diogo Rodrigo Theo Novaes")
                .cpf("560.270.359-43")
                .rg("10.586.140-6")
                .cep("58026-070")
                .logradouro("Rua Morada Nova")
                .numero("391")
                .bairro("Treze de Maio")
                .cidade("João Pessoa")
                .estado("João Pessoa")
                .telefone("(83) 2703-8046")
                .celular("(83) 98694-6301")
                .email("diogo.rodrigo.novaes@diebold.com")
                .username("diogo.rodrigo.novaes@diebold.com")
                .password("m4y4KxmMTf")
                .clinica(clinica)
                .crmv("123456")
                .build();

        return Prontuario.builder()
                .codigo("code")
                .dataAtendimento(
                        LocalDateTime.now()
                )
                .animal(
                        Animal.builder()
                                .especie("gato")
                                .nome("miau")
                                .anoNascimento(2020)
                                .raca("Siames")
                                .pelagem("curta")
                                .sexo(SexoAnimal.FEMEA)
                                .tutores(
                                        List.of(tutor)
                                )
                                .build())
                .clinica(clinica)
                .tutor(tutor)
                .veterinario(veterinario)
                .procedimentos(
                        List.of(
                                Procedimento.builder()
                                    .descricao("Vacinação")
                                    .medicamentoConsumido(
                                                    Estoque.builder()
                                                        .medida("ml")
                                                        .quantidade(new BigDecimal("50.5"))
//                                                        .clinica(clinica)
                                                        .medicamento(
                                                                Medicamento.builder()
                                                                        .codigoRegistro("12345689")
                                                                        .concentracao("500mg")
                                                                        .nome("Vacina")
                                                                        .viaUso("Intravenoso")
                                                                        .principioAtivo("Virus Inativado")
                                                                        .build()
                                                        ).build()
                                    )
                                        .build()
                        )
                ).build();
    }

    private Prontuario getProntuarioInstanceWithDocumento(String documentoTipo){
        Clinica clinica = Clinica.builder()
                .cidade("Cidade das Abelhas")
                .razaoSocial("Clinica vet")
                .telefone("(12) 3456-7890")
                .build();
        Usuario tutor = Usuario.builder()
                .nome("Caio Felipe Pires")
                .cpf("175.578.151-22")
                .rg("13.123.399-3")
                .cep("66053-140")
                .logradouro("Praça Magalhães")
                .numero("242")
                .bairro("Reduto")
                .cidade("Belém")
                .estado("PA")
                .telefone("(91) 2792-2741")
                .celular("(91) 99850-3799")
                .email("caio_pires@lanchesdahora.com.br")
                .username("caio_pires@lanchesdahora.com.br")
                .password("6EzlRrYEzy")
                .clinica(clinica)
                .build();
        Usuario veterinario = Usuario.builder()
                .nome("Diogo Rodrigo Theo Novaes")
                .cpf("560.270.359-43")
                .rg("10.586.140-6")
                .cep("58026-070")
                .logradouro("Rua Morada Nova")
                .numero("391")
                .bairro("Treze de Maio")
                .cidade("João Pessoa")
                .estado("João Pessoa")
                .telefone("(83) 2703-8046")
                .celular("(83) 98694-6301")
                .email("diogo.rodrigo.novaes@diebold.com")
                .username("diogo.rodrigo.novaes@diebold.com")
                .password("m4y4KxmMTf")
                .clinica(clinica)
                .crmv("123456")
                .build();
        Documento documento = Documento.builder()
                .tipo(documentoTipo)
                .build();

        return Prontuario.builder()
                .codigo("code")
                .dataAtendimento(
                        LocalDateTime.now()
                )
                .animal(
                        Animal.builder()
                                .especie("gato")
                                .nome("miau")
                                .anoNascimento(2020)
                                .raca("Siames")
                                .pelagem("curta")
                                .sexo(SexoAnimal.FEMEA)
                                .tutores(
                                        List.of(tutor)
                                )
                                .build())
                .clinica(clinica)
                .tutor(tutor)
                .veterinario(veterinario)
                .documentos(List.of(documento))
                .procedimentos(
                        List.of(
                                Procedimento.builder()
                                    .descricao("Vacinação")
                                    .medicamentoConsumido(
                                                    Estoque.builder()
                                                        .medida("ml")
                                                        .quantidade(new BigDecimal("50.5"))
//                                                        .clinica(clinica)
                                                        .medicamento(
                                                                Medicamento.builder()
                                                                        .codigoRegistro("12345689")
                                                                        .concentracao("500mg")
                                                                        .nome("Vacina")
                                                                        .viaUso("Intravenoso")
                                                                        .principioAtivo("Virus Inativado")
                                                                        .build()
                                                        ).build()
                                    )
                                        .build()
                        )
                ).build();
    }

    private Documento getDocumento() {
        return Documento.builder()
                .codigo("code")
//                .tipo("sanitario")
                .build();
    }

    @Test
    @DisplayName("Devolve um PDF de prontuario gerado a partir de HTML")
    void whenRequestProntuarioPdf_ThenReturnPdfFile() throws Exception {

        Prontuario parametro = getProntuarioInstance();
        File outputFile = new File("src/test/resources/prontuario/htmlToPdf/test.pdf");
        Files.write(outputFile.toPath(), service.writeProntuario(getProntuarioInstance()));
    }

    //TODO: Analisar a forma que os documentos, além do doc_sanitario, estão sendo construídos pois quebram por erro de substituição de variável ou espaço a mais no texto
    @ParameterizedTest
    @ValueSource(strings = {
            "sanitario"
//            "anestesia",
//            "cirurgia",
//            "doacaoPesquisa",
//            "eutanasia",
//            "exames",
//            "retiraCorpo",
//            "retiradaSemAlta",
//            "terapeutico",
//            "tratamentoClinico",
//            "vacinacao"
    })
    @DisplayName("Devolve um PDF de documento gerado a partir de HTML de acordo com o parametro solicitado")
    /*
     * https://www.baeldung.com/parameterized-tests-junit-5
     */
    void readPDF(final String documentoTipo) throws Exception {
        final String path = "src/test/resources/prontuario/htmlToPdf/";
        final File parameterFile = new File(path + "doc_" + documentoTipo + ".txt");
        final File outputFile = new File(path + "test_documento_" + documentoTipo+ ".pdf");
        when(documentoService.provideLayout(documentoTipo))
                .thenReturn(new DocumentoServiceImpl()
                        .provideLayout(documentoTipo));
        when(documentoRepository.save(any())).thenReturn(Documento.builder().tipo(documentoTipo).build());

        Files.write(
                outputFile.toPath(),
                service.writePdfDocumentoEmBranco(
                        getProntuarioInstanceWithDocumento(documentoTipo),
                        documentoService.provideLayout(documentoTipo)));
        final String txtFromPdf = new PDFTextStripper().getText(
                PDDocument.load(outputFile));

        final var mes = new SimpleDateFormat("MMMMM", L).format(DATE);
        String parameterTxt = new BufferedReader(new FileReader(parameterFile)).lines()
                .map(t -> t.replace(
                        "Cidade das Abelhas, 1 de Abril de 2023.",
                        "Cidade das Abelhas, "
                                + new SimpleDateFormat("d 'de' ", L).format(DATE)
                                + mes.substring(0, 1)
                                .toUpperCase()
                                + mes.substring(1)
                                + new SimpleDateFormat(" 'de' yyyy", L).format(DATE)
                                + ".")
                        .replace("${veterinario.crmv}", "123456")
                ).collect(Collectors.joining("\r\n", "", ""));

        String[] txtFromPdfLines = txtFromPdf.split("\\r?\\n");
        String[] parameterTxtLines = parameterTxt.split("\\r?\\n");

        List<String> filteredTxtFromPdfLines = Arrays.stream(txtFromPdfLines)
                .filter(line -> !line.trim().isEmpty()).toList();

        List<String> filteredParameterTxtLines = Arrays.stream(parameterTxtLines)
                .filter(line -> !line.trim().isEmpty()).toList();

        boolean pdfIsVald = filteredTxtFromPdfLines.equals(filteredParameterTxtLines);

        assertTrue(pdfIsVald);

    }

    @Test
    public void retrievingProntuarioFromRepository() throws IOException {
        Prontuario prontuario = getProntuarioInstance();
        byte[] retrievedProntuario = service.retrieveFromRepository(prontuario);

        assertNotNull(retrievedProntuario);
        // result está sendo [] provavelmente por não estar buscando no DB, comportamento esperado de acordo com a atual implementação da function
    }

//    @Test
//    void settingProtectionToDocument() throws IOException {
//        // 1. criar um documento válido, deve ser em byte[] p/ ser compativel com a function testada
//        // 2. criar um prontuário, getProntuarioInstance() é utilizado acima
//        // 3. chamar a function setProtection
//            // 3.1. setProtection precisa: CPF válido p/ getTutorPass() e OWNER_PASSWORD pré-definida
//
//        // PROBLEMAS:
//        // OWNER_PASSWORD está null, não consegui bolar um jeito de mockar essa info
//        // assert de retorno not null pois o return da function é byte[]. Criar um boolean com esse retorno p/ validar que o documento teve proteção setada? Tipo, o byte retornado é o do documento? ele será diferente do byte do documento do input?
//
//        String documentoTipo = "sanitario"; // alterar p/ @ParameterizedTest depois
//        when(documentoService.provideLayout(documentoTipo))
//                .thenReturn(new DocumentoServiceImpl()
//                        .provideLayout(documentoTipo));
//
//        when(documentoRepository.save(any())).thenReturn(Documento.builder().build());
//
//        // 1.
////        byte[] documento = service.writePdfDocumentoEmBranco(
////                        getProntuarioInstance(),
////                        documentoService.provideLayout(documentoTipo));
//
//        // 2.
//        Prontuario prontuario = getProntuarioInstance();
//
//        // 3.
//        byte[] returnedDocument = service.setProtection(Files.readAllBytes(Path.of("src/test/resources/prontuario/htmlToPdf/test_documento_sanitario.pdf")), prontuario);
//
//        // 4.
//        assertNotNull(returnedDocument);
//    }

}

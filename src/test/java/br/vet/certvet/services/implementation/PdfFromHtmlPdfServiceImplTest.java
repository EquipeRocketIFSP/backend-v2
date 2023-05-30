package br.vet.certvet.services.implementation;

import br.vet.certvet.enums.SexoAnimal;
import br.vet.certvet.models.*;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.DocumentoService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class PdfFromHtmlPdfServiceImplTest {

    public static final Date DATE = new Date();
    public static final Locale L = new Locale("pt", "BR");

    @Mock private ProntuarioRepository prontuarioRepository;// = mock(ProntuarioRepository.class);

    @Mock private ClinicaRepository clinicaRepository; // = mock(ClinicaRepository.class);

    @Mock private PdfRepository pdfRepository;

    @Mock private DocumentoService documentoService;

    @InjectMocks
    @Qualifier("pdfFromHtmlPdfServiceImpl")
    private PdfFromHtmlPdfServiceImpl service;

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown(){
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
                                    .medicamentosConsumidos(
                                            List.of(
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
                                    ).build()
                        )
                ).build();
    }

    @Test
    @DisplayName("Devolve um PDF de prontuario gerado a partir de HTML")
    void whenRequestProntuarioPdf_ThenReturnPdfFile() throws Exception {

        Prontuario parametro = getProntuarioInstance();
        File outputFile = new File("src/test/resources/prontuario/htmlToPdf/test.pdf");
        Files.write(outputFile.toPath(), service.writeProntuario(getProntuarioInstance()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "sanitario",
            "anestesia",
            "cirurgia",
            "doacaoPesquisa",
            "eutanasia",
            "exames",
            "retiraCorpo",
            "retiradaSemAlta",
            "terapeutico",
            "tratamentoClinico",
            "vacinacao"
            //verificar o loop causado pelo doc de obito
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

        Files.write(
                outputFile.toPath(),
                service.writePdfDocumentoEmBranco(
                        getProntuarioInstance(),
                        documentoService.provideLayout(documentoTipo)));
        final String txtFromPdf = new PDFTextStripper().getText(
                PDDocument.load(outputFile));

        final var mes = new SimpleDateFormat("MMMMM", L).format(DATE);
        final var parameterTxt = new BufferedReader(new FileReader(parameterFile)).lines()
                .map(t -> t.replace(
                        "Cidade das Abelhas, 1 de Abril de 2023.",
                        "Cidade das Abelhas, "
                                + new SimpleDateFormat("d 'de' ", L).format(DATE)
                                + mes.substring(0,1)
                                .toUpperCase()
                                + mes.substring(1)
                                + new SimpleDateFormat(" 'de' yyyy", L).format(DATE)
                                + ".")).collect(Collectors.joining("\r\n", " ", ""));

        final var result = txtFromPdf.contains(parameterTxt);
        if (result)
            System.out.println("Documentos iguais, retornar um sucesso");
//        mudar de void p/ exception e retornar um exception em caso de erro? OU testar um executable no assertions?
    }
}

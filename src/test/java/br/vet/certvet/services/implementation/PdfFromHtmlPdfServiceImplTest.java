package br.vet.certvet.services.implementation;

import br.vet.certvet.enums.SexoAnimal;
import br.vet.certvet.models.*;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.implementation.PdfFromHtmlPdfServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
public class PdfFromHtmlPdfServiceImplTest {

    @Mock private ProntuarioRepository prontuarioRepository;// = mock(ProntuarioRepository.class);

    @Mock private ClinicaRepository clinicaRepository; // = mock(ClinicaRepository.class);

    @Mock private PdfRepository pdfRepository;

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
                .dataAtendimento(LocalDateTime.now())
                .animal(Animal.builder()
                        .especie("gato")
                        .nome("miau")
                        .idade(2)
                        .raca("Siames")
                        .pelagem("curta")
                        .sexo(SexoAnimal.FEMEA)
                        .tutores(List.of(tutor))
                        .build())
                .clinica(clinica)
                .tutor(tutor)
                .veterinario(veterinario)
                .procedimentos(List.of(
                        Procedimento.builder()
                                .descricao("Vacinação")
                                .medicamentosConsumidos(List.of(
                                        Estoque.builder()
                                                .medida("ml")
                                                .quantidade(new BigDecimal("50.5"))
                                                .clinica(clinica)
                                                .medicamento(Medicamento.builder()
                                                        .codigoRegistro("12345689")
                                                        .concentracao("500mg")
                                                        .nome("Vacina")
                                                        .viaUso("Intravenoso")
                                                        .principioAtivo("Virus Inativado")
                                                        .build())
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }

    @Test
    @DisplayName("Devolve um PDF de prontuario gerado a partir de HTML")
    void whenRequestProntuarioPdf_ThenReturnPdfFile() throws Exception {

        Prontuario parametro = getProntuarioInstance();
        File outputFile = new File("src/test/resources/prontuario/htmlToPdf/test.pdf");
        Files.write(outputFile.toPath(), service.writeProntuario(getProntuarioInstance()));
    }

    @Test
    @DisplayName("Devolve um PDF de documento gerado a partir de HTML")
    void whenRequestDocumentoPdf_ThenReturnPdfFile() throws Exception {

        Prontuario parametro = getProntuarioInstance();
        File outputFile = new File("src/test/resources/prontuario/htmlToPdf/test_documento.pdf");
        Files.write(outputFile.toPath(), service.writeDocumento(getProntuarioInstance(), "sanitario"));
    }
}

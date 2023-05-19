package br.vet.certvet.services.implementation;

import br.vet.certvet.repositories.PdfRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class PdfRepositoryImplTest {

    private final PdfRepository pdfRepository = new S3BucketServiceRepository();

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown(){
    }

    @Test
    void whenPdfFileIsCreated_ThenShouldKeepItInAwsS3() throws IOException {
        final String cnpj = "81.718.422/0001-07";
        final String file = "test_documento_sanitario_assinado.pdf";
        final byte[] pdf = Files.readAllBytes(Path.of("src/test/resources/prontuario/htmlToPdf/" + file));
        var res = pdfRepository.putObject(cnpj, file, pdf);
        assertNotNull(res);

    }

}

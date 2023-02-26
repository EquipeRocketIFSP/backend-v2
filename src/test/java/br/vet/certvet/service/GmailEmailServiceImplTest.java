package br.vet.certvet.service;

import br.vet.certvet.services.EmailService;
import br.vet.certvet.services.implementation.GmailEmailServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
public class GmailEmailServiceImplTest {

    @Qualifier("gmailEmailServiceImpl")
    @Autowired
    private EmailService service;
    private File sample;
    @Value("${spring.mail.username}")
    private String sender;

    @BeforeEach
    void setUp(){
        sample = new File("src/test/resources/sample.pdf");
    }

    @AfterEach
    void tearDown(){
        service = null;
        sample = null;
    }

    @Test
    @DisplayName("Não deve lançar exceção")
    /**
     * Em caso de quebra por credenciais, verificar credenciais nos comenários em:
     * https://rocket-ifsp.atlassian.net/jira/software/projects/ERI/boards/1?selectedIssue=ERI-397
     */
    void whenSendSimpleMailReceivesText_thenNotThrowException(){
        assertDoesNotThrow(
                () -> service.sendTextMessage(sender,"Sample Message Title", "Sample Message Body")
        );
    }


}

package br.vet.certvet.services.implementation;

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
import org.springframework.test.context.ActiveProfiles;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
public class GmailEmailServiceImplTest {

    @Autowired
    private GmailEmailServiceImpl service;
    @Value("${spring.mail.username}")
    private String to;

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown(){
    }

    @Test
    @DisplayName("Não deve lançar exceção")
    /**
     * Em caso de quebra por credenciais, verificar credenciais nos comenários em:
     * https://rocket-ifsp.atlassian.net/jira/software/projects/ERI/boards/1?selectedIssue=ERI-397
     */
    void whenSendSimpleMailReceivesText_ThenNotThrowException(){
        assertDoesNotThrow(
                () -> service.sendTextMessage(to,"Sample Message Title", "Sample Message Body")
        );
    }

    @Test
    @DisplayName("Envia email com anexo sem lançar exceção")
    void whenSendEmailWithAttachment_ThenDoesNotThrowException(){
        assertDoesNotThrow(
                () -> service.sendMessageWithAttachment(to,"Sample Message Title with attachment", "Sample Message Body with attachment", "sample.pdf", "src/test/resources/sample.pdf")
        );
    }

}

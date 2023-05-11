package br.vet.certvet.services.implementation;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
class ProntuarioServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createProntuario() {
    }

    @Test
    void editProntuario() {
    }

    @Test
    void getProntuarioById() {
    }

    @Test
    void deleteProntuario() {
    }
}

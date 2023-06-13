package br.vet.certvet.services.implementation;

import br.vet.certvet.models.Documento;
import br.vet.certvet.repositories.DocumentoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class DocumentoServiceImplTest {

    @Mock
    private DocumentoRepository documentoRepository;

    @InjectMocks
    private DocumentoServiceImpl documentoService;

    @BeforeEach
    void setUp(){}

    @AfterEach
    void tearDown(){}

    @Test
    @DisplayName("Busca pelo código deve devolver uma instância de Documento")
    void whenSearchByCodigo_thenReturnsDocumentoFromRepository(){
        final String codigo = "expectedCode";
        final Documento expected = Documento.builder().codigo(codigo).build();
        when(documentoRepository.findByCodigo(any())).thenReturn(Optional.of(expected));

        final Documento actual = documentoService.getByCodigo(codigo).stream().findFirst().orElseThrow(IllegalArgumentException::new);

        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Criação de documento deve devolver uma instância de Documento")
    void whenSave_thenReturnsDocumentoFromRepository(){
        final Documento expected = Documento.builder().build();
        when(documentoRepository.save(expected)).thenReturn(expected);

        final Documento actual = documentoService.save(expected);

        assertEquals(expected, actual);
    }

}

package br.vet.certvet.services.implementation;


import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Procedimento;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ProcedimentoServiceImplTest {

    @Mock private ProcedimentoRepository procedimentoRepository;
//    @InjectMocks
    private ProcedimentoServiceImpl procedimentoServiceImpl;

    @BeforeEach
    void setUp() {
        procedimentoServiceImpl = new ProcedimentoServiceImpl(procedimentoRepository);
    }

    @AfterEach
    void tearDown() {
        procedimentoServiceImpl = null;
    }

    @Test
    @DisplayName("Quanto salvar prescrição, deve retornar um objeto Procedimento com 'id' da prescricao populado")
    void savePrescricao(){
        List<Prescricao> emptyList = new ArrayList<>();
        Procedimento procedimento = procedimento(emptyList);
        assertEquals(emptyList, procedimento.getPrescricao());

        List<Prescricao> prescricoes = prescricaoList(procedimento, null);
        when(procedimentoRepository.save(any())).thenReturn(procedimento(prescricoes));
        procedimento = procedimentoServiceImpl.savePrescricao(procedimento, prescricoes);
        prescricoes = prescricaoList(procedimento, 1L);
        assertEquals(prescricoes, procedimento.getPrescricao());

    }

    @Test
    @DisplayName("Quando salvar um procedimento, deve retornar um objeto Procedimento com 'id' da prescricao populado")
    void save() {
        Procedimento procedimento = procedimento(List.of(
                Prescricao.builder()
                        .uso("teste")
                        .nome("teste")
                        .dose("teste")
                        .formaFarmaceutica("teste")
                        .concentracao("teste")
                        .frequencia("teste")
                        .duracao("teste")
                        .quandoAplicar("teste")
                        .id(null)
                        .build()
        ));

        var expected = procedimento(List.of(
                Prescricao.builder()
                        .uso("teste")
                        .nome("teste")
                        .dose("teste")
                        .formaFarmaceutica("teste")
                        .concentracao("teste")
                        .frequencia("teste")
                        .duracao("teste")
                        .quandoAplicar("teste")
                        .id(1L)
                        .build()
        ));

        when(procedimentoRepository.save(any())).thenReturn(expected);

        var actual = procedimentoServiceImpl.save(procedimento);
        assertEquals(expected, actual);
    }

    private Procedimento procedimento(List<Prescricao> prescricoes){
        return Procedimento.builder()
                .prescricao(prescricoes)
                .build();
    }

    private List<Prescricao> prescricaoList(Procedimento procedimento, Long id){
        List<Prescricao> l = new ArrayList<>();
        l.add(
                Prescricao.builder()
                    .uso("teste")
                    .nome("teste")
                    .dose("teste")
                    .formaFarmaceutica("teste")
                    .concentracao("teste")
                    .frequencia("teste")
                    .duracao("teste")
                    .quandoAplicar("teste")
                    .id(id)
                    .procedimento(procedimento)
                    .build());
        return l;
    }
}

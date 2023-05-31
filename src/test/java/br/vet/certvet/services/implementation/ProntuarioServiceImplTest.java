package br.vet.certvet.services.implementation;


import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.dto.requests.prontuario.SinaisVitaisDTO;
import br.vet.certvet.enums.SexoAnimal;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.*;
import br.vet.certvet.models.factories.ProntuarioFactory;
import br.vet.certvet.repositories.*;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.unit.UsuarioTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


//@SpringBootTest
//@ActiveProfiles("test")
//@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
class ProntuarioServiceImplTest {

    private ProntuarioRepository prontuarioRepository = mock(ProntuarioRepository.class);

    private PdfRepository pdfRepository = mock(PdfRepository.class);

    private CirurgiaRepository cirurgiaRepository = mock(CirurgiaRepository.class);

    private TutorRepository tutorRepository = mock(TutorRepository.class);

    private DocumentoRepository documentoRepository = mock(DocumentoRepository.class);

    private PdfService pdfService = mock(PdfService.class);

    private ClinicaRepository clinicaRepository = mock(ClinicaRepository.class);

    private AnimalRepository animalRepository = mock(AnimalRepository.class);

    private DocumentoService documentoService = mock(DocumentoService.class);

    private ProntuarioFactory prontuarioFactory = mock(ProntuarioFactory.class);

    @Qualifier("prontuarioServiceImpl")
    private ProntuarioServiceImpl prontuarioService;

    @BeforeEach
    void setUp() {
        prontuarioService = new ProntuarioServiceImpl(
                prontuarioRepository,
                pdfRepository,
                cirurgiaRepository,
                tutorRepository,
                documentoRepository,
                pdfService,
                clinicaRepository,
                animalRepository,
                documentoService
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createProntuario() {
        SinaisVitaisDTO dto = factorySinaisVitaisDTO();
        final Prontuario prontuarioExpected = getProntuarioInstance();

        when(prontuarioRepository.saveAndFlush(any())).thenReturn(prontuarioExpected);

        Prontuario prontuarioCreated = prontuarioService.create(dto, getAnimal(), getTutor(), getVeterinario());

        assertNotNull(prontuarioCreated);
        assertEquals(prontuarioExpected, prontuarioCreated);
    }

    @Test
    void editProntuario() {
        SinaisVitaisDTO dto = factorySinaisVitaisDTO();
        final Prontuario referenceProntuario = getProntuarioInstance();

        when(prontuarioRepository.saveAndFlush(any())).thenReturn(
                Prontuario.builder()
                        .id(1L)
                        .codigo("code")
                        .animal(getAnimal())
                        .clinica(getClinica())
                        .tutor(getTutor())
                        .veterinario(getVeterinario())
                        .conciencia("conciencia")
                        .mucosa("mucosa")
                        .peso("4")
                        .tpc("tpc")
                        .hidratacao("hidratacao")
                        .escoreCorporal("escore corporal")
                        .frequenciaCardiaca(12)
                        .frequenciaRespiratoria(13)
                        .temperatura(14)
                        .build()
        );

        Prontuario prontuarioToBeEdited = prontuarioService.create(dto, getAnimal(), getTutor(), getVeterinario());

        SinaisVitaisDTO dtoForEdition = factorySinaisVitaisDTOForEdition();
        Prontuario editedProntuario = prontuarioService.edit(dtoForEdition, prontuarioToBeEdited);

        assertThat(editedProntuario).isNotEqualTo(referenceProntuario);
        assertThat(editedProntuario.getPeso()).isEqualTo("5");
    }

    //TODO: PEDE AJUDA!
    @Test
    void getProntuarioById() {
        SinaisVitaisDTO dto = factorySinaisVitaisDTO();
        final Prontuario prontuarioExpected = getProntuarioInstance();

        when(prontuarioRepository.saveAndFlush(any())).thenReturn(
                Prontuario.builder()
                        .id(1L)
                        .codigo("code")
                        .animal(getAnimal())
                        .clinica(getClinica())
                        .tutor(getTutor())
                        .veterinario(getVeterinario())
                        .conciencia("conciencia")
                        .mucosa("mucosa")
                        .peso("4")
                        .tpc("tpc")
                        .hidratacao("hidratacao")
                        .escoreCorporal("escore corporal")
                        .frequenciaCardiaca(12)
                        .frequenciaRespiratoria(13)
                        .temperatura(14)
                        .build()
        );
        when(prontuarioRepository.findById(any())).thenReturn(
                Optional.ofNullable(
                        Prontuario.builder()
                            .id(1L)
                            .codigo("code")
                            .animal(getAnimal())
                            .clinica(getClinica())
                            .tutor(getTutor())
                            .veterinario(getVeterinario())
                            .conciencia("conciencia")
                            .mucosa("mucosa")
                            .peso("4")
                            .tpc("tpc")
                            .hidratacao("hidratacao")
                            .escoreCorporal("escore corporal")
                            .frequenciaCardiaca(12)
                            .frequenciaRespiratoria(13)
                            .temperatura(14)
                            .build()
                )
        );

        Prontuario prontuarioCreated = prontuarioService.create(dto, getAnimal(), getTutor(), getVeterinario());
        //Prontuario criado não está recebendo valor algum p/ id. Isso é com logica no banco?
        Optional<Prontuario> foundProntuario = prontuarioService.findById(prontuarioExpected.getId());

        assertNotNull(foundProntuario);
//        assertThat(Optional.of(prontuarioCreated)).isEqualTo(foundProntuario);
    }

    @Test
    void findOneProntuario() {
        SinaisVitaisDTO dto = factorySinaisVitaisDTO();

        when(prontuarioRepository.saveAndFlush(any())).thenReturn(
                Prontuario.builder()
                        .id(1L)
                        .codigo("code")
                        .animal(getAnimal())
                        .clinica(getClinica())
                        .tutor(getTutor())
                        .veterinario(getVeterinario())
                        .conciencia("conciencia")
                        .mucosa("mucosa")
                        .peso("4")
                        .tpc("tpc")
                        .hidratacao("hidratacao")
                        .escoreCorporal("escore corporal")
                        .frequenciaCardiaca(12)
                        .frequenciaRespiratoria(13)
                        .temperatura(14)
                        .build()
        );
        when(prontuarioRepository.findOneByIdAndAnimal(any(), any())).thenReturn(
                Optional.ofNullable(
                        Prontuario.builder()
                            .id(1L)
                            .codigo("code")
                            .animal(getAnimal())
                            .clinica(getClinica())
                            .tutor(getTutor())
                            .veterinario(getVeterinario())
                            .conciencia("conciencia")
                            .mucosa("mucosa")
                            .peso("4")
                            .tpc("tpc")
                            .hidratacao("hidratacao")
                            .escoreCorporal("escore corporal")
                            .frequenciaCardiaca(12)
                            .frequenciaRespiratoria(13)
                            .temperatura(14)
                            .build()
                )
        );

        Prontuario prontuarioCreated = prontuarioService.create(dto, getAnimal(), getTutor(), getVeterinario());
        Prontuario prontuarioFound = prontuarioService.findOne(1L, getAnimal());

        assertNotNull(prontuarioFound);
//        assertEquals(prontuarioFound, prontuarioCreated);
    }

    @Test
    void getProntuarioByCodigo() {
        SinaisVitaisDTO dto = factorySinaisVitaisDTO();
        final Prontuario prontuarioExpected = getProntuarioInstance();

        when(prontuarioRepository.saveAndFlush(any())).thenReturn(prontuarioExpected);
        when(prontuarioRepository.findByCodigo(any())).thenReturn(Optional.ofNullable(prontuarioExpected));

        Prontuario prontuarioCreated = prontuarioService.create(dto, getAnimal(), getTutor(), getVeterinario());
        Optional<Prontuario> foundProntuario = prontuarioService.findByCodigo(prontuarioCreated.getCodigo());

        assertNotNull(foundProntuario);
        assertEquals(Optional.of(prontuarioCreated), foundProntuario);
    }

    @Test
    void deleteProntuario() {
        SinaisVitaisDTO dto = factorySinaisVitaisDTO();
        Prontuario prontuario = prontuarioService.create(dto, getAnimal(), getTutor(), getVeterinario());

        Boolean prontuarioDeleted = prontuarioService.deleteProntuario(prontuario);

        assertFalse(prontuarioDeleted);
    }

    @Test
    void retrievingPdfFromRepository() throws IOException {
        SinaisVitaisDTO dto = factorySinaisVitaisDTO();
        final Prontuario prontuarioExpected = getProntuarioInstance();
        //TODO: pdfByteReference gerado randomicamente p/ mockar a function, ver se alguém indica algo melhor
        byte[] pdfByteReference = new byte[]{0x12, 0x34, 0x56, 0x78, (byte) 0x9A};

        when(prontuarioRepository.saveAndFlush(any())).thenReturn(prontuarioExpected);
        when(pdfRepository.retrieveObject(any(), any())).thenReturn(Optional.of(pdfByteReference));

        Prontuario prontuarioCreated = prontuarioService.create(dto, getAnimal(), getTutor(), getVeterinario());
        Optional<byte[]> retrievedPdf = prontuarioService.retrievePdfFromRepository(prontuarioCreated);

        assertNotNull(retrievedPdf);
    }

    @Test
    //TODO: pensar num nome melhor, isso ta feião
    void verifyingProntuarioExistence() {
        SinaisVitaisDTO dto = factorySinaisVitaisDTO();
        final Prontuario prontuarioExpected = getProntuarioInstance();

        when(prontuarioRepository.saveAndFlush(any())).thenReturn(prontuarioExpected);
        when(prontuarioRepository.existsByDataAtendimento(any())).thenReturn(true);

        Prontuario prontuario = prontuarioService.create(dto, getAnimal(), getTutor(), getVeterinario());
        boolean isProntuarioExistent = prontuarioService.exists(prontuario.getDataAtendimento());

        assertTrue(isProntuarioExistent);
        assertEquals(prontuarioExpected.getDataAtendimento(), prontuario.getDataAtendimento());
    }

//    @Test
//    void saveProntuario() throws Exception {
//        SinaisVitaisDTO dto = factorySinaisVitaisDTO();
//        final Prontuario prontuarioExpected = getProntuarioInstance();
//        //TODO: pdfByteReference gerado randomicamente p/ mockar a function, ver se alguém indica algo melhor
//        byte[] pdfByteReference = new byte[]{0x12, 0x34, 0x56, 0x78, (byte) 0x9A};
//
//        when(prontuarioRepository.saveAndFlush(any())).thenReturn(
//                Prontuario.builder()
//                        .id(1L)
//                        .codigo("code")
//                        .animal(getAnimal())
//                        .clinica(getClinica())
//                        .tutor(getTutor())
//                        .veterinario(getVeterinario())
//                        .conciencia("conciencia")
//                        .mucosa("mucosa")
//                        .peso("4")
//                        .tpc("tpc")
//                        .hidratacao("hidratacao")
//                        .escoreCorporal("escore corporal")
//                        .frequenciaCardiaca(12)
//                        .frequenciaRespiratoria(13)
//                        .temperatura(14)
//                        .build()
//        );
//        when(clinicaRepository.findById(any())).thenReturn(Optional.ofNullable(getClinica()));
//        when(tutorRepository.findById(any())).thenReturn(Optional.ofNullable(getTutor()));
//        when(animalRepository.findByTutores_idAndNome(any(), any())).thenReturn(Optional.ofNullable(getAnimal()));
//        when(pdfService.writeProntuario(any())).thenReturn(pdfByteReference);
//
//        Prontuario prontuarioCreated = prontuarioService.create(dto, getAnimal(), getTutor(), getVeterinario());
//        Prontuario savedProntuario = prontuarioService.save(prontuarioCreated);
//
//        assertNotNull(savedProntuario);
//    }

    private ProntuarioDTO factoryProntuarioDTO() {
        ProntuarioDTO dto = new ProntuarioDTO();

        dto.setAnimal(getAnimal().getId());
        dto.setTutor(getTutor().getId());
        dto.setVeterinario(getVeterinario().getId());

        return dto;
    }

    private SinaisVitaisDTO factorySinaisVitaisDTO() {
        SinaisVitaisDTO dto = new SinaisVitaisDTO();

        dto.setAnimal(getAnimal().getId());
        dto.setTutor(getTutor().getId());
        dto.setVeterinario(getVeterinario().getId());
        dto.setConciencia("Conciencia");
        dto.setMucosa("Mucosa");
        dto.setPeso("4");
        dto.setTpc("TPC");
        dto.setHidratacao("Hidratacao");
        dto.setEscoreCorporal("Escore");
        dto.setFrequenciaCardiaca(12);
        dto.setFrequenciaRespiratoria(13);
        dto.setTemperatura(14);

        return dto;
    }

    private SinaisVitaisDTO factorySinaisVitaisDTOForEdition() {
        SinaisVitaisDTO dto = new SinaisVitaisDTO();

        dto.setAnimal(getAnimal().getId());
        dto.setTutor(getTutor().getId());
        dto.setVeterinario(getVeterinario().getId());
        dto.setConciencia("Conciencia");
        dto.setMucosa("Mucosa");
        dto.setPeso("5");
        dto.setTpc("TPC");
        dto.setHidratacao("Hidratacao");
        dto.setEscoreCorporal("Escore");
        dto.setFrequenciaCardiaca(6);
        dto.setFrequenciaRespiratoria(7);
        dto.setTemperatura(8);

        return dto;
    }

    private Animal getAnimal() {
        return Animal.builder()
                .id(1L)
                .nome("Amy")
                .peso(4.0f)
                .especie("canina")
                .raca("Pinscher")
                .sexo(SexoAnimal.valueOf("FEMEA"))
                .pelagem("curta")
                .anoNascimento(1205)
                .tutores(List.of(getTutor()))
                .build();
    }

    private Usuario getTutor() {
        Authority authority = Authority.builder()
                .id(1L)
                .authority("TUTOR")
                .build();
        Clinica clinica = Clinica.builder()
                .cidade("Cidade das Abelhas")
                .razaoSocial("Clinica vet")
                .telefone("(12) 3456-7890")
                .build();
        return Usuario.builder()
                .id(1L)
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
                .authorities(List.of(authority))
                .clinica(clinica)
                .build();
    }

    private Usuario getVeterinario() {
        Authority authority = Authority.builder()
                .id(2L)
                .authority("VETERINARIO")
                .build();
        Clinica clinica = Clinica.builder()
                .cidade("Cidade das Abelhas")
                .razaoSocial("Clinica vet")
                .telefone("(12) 3456-7890")
                .build();
        return Usuario.builder()
                .id(2L)
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
                .authorities(List.of(authority))
                .build();
    }

    private Prontuario getProntuarioInstance() {
        Clinica clinica = Clinica.builder()
                .cidade("Cidade das Abelhas")
                .razaoSocial("Clinica vet")
                .telefone("(12) 3456-7890")
                .build();
        Authority authorityTutor = Authority.builder()
                .id(1L)
                .authority("TUTOR")
                .build();
        Authority authorityVeterinario = Authority.builder()
                .id(2L)
                .authority("VETERINARIO")
                .build();
        Usuario tutor = Usuario.builder()
                .id(1L)
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
                .authorities(List.of(authorityTutor))
                .clinica(clinica)
                .build();
        Usuario veterinario = Usuario.builder()
                .id(2L)
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
                .authorities(List.of(authorityVeterinario))
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
                .build();
    }

    private Clinica getClinica() {
        return Clinica.builder()
                .cidade("Cidade das Abelhas")
                .razaoSocial("Clinica vet")
                .telefone("(12) 3456-7890")
                .cnpj("66.231.439/0001-33")
                .build();
    }

}

package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.enums.SexoAnimal;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.AnimalRepository;
import br.vet.certvet.services.UsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class AnimalServiceImplTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private AnimalRepository animalRepository;

    @Qualifier("animalServiceImpl")
    private AnimalServiceImpl animalService;

    @BeforeEach
    void setUp(){
        animalService = new AnimalServiceImpl(usuarioService, animalRepository);
    }

    @AfterEach
    void tearDown(){
        animalService = null;
    }

    @Test
    void createAnimal() {
        AnimalRequestDto dto = factoryAnimalRequestDto();
        final Animal animalExpected = new Animal(dto);

        when(this.animalRepository.saveAndFlush(any())).thenReturn(animalExpected);
        when(this.usuarioService.findUsuarioAuthority(any(), eq("TUTOR")))
                .thenReturn(
                        Optional.of(
                                Authority.builder()
                                        .id(1L)
                                        .permissao("TUTOR")
                                        .build()
                        )
                );

        final Animal animalCreated = this.animalService.create(dto, List.of(getTutor()));

        assertEquals(animalCreated, animalExpected);
    }

    @Test
    void getExistentAnimal(){
        Usuario tutor = getTutor();
        Optional<Animal> expected = Optional.of(
                Animal.builder()
                        .id(1L)
                        .nome("Amy")
                        .anoNascimento(1205)
                        .peso(4.0f)
                        .raca("Pinscher")
                        .especie("canina")
                        .pelagem("curta")
                        .sexo(SexoAnimal.valueOf("FEMEA"))
                        .build()
        );
        when(
                animalRepository.findOneByIdAndTutores(
                        expected.get().getId(),
                        tutor
                )
        ).thenReturn(
                expected
        );

        Animal existentAnimal = animalService.findOne(expected.get().getId(), getTutor());

        assertEquals(expected.get(), existentAnimal);
    }

    @Test
    void getNonExistentAnimal(){
        Long unavailableId = 9999999999L;
        assertThrowsExactly(NotFoundException.class, () -> this.animalService.findOne(unavailableId, getTutor()));
    }

    @Test
    void editExistentAnimal(){
        AnimalRequestDto dto = factoryAnimalRequestDto();
        Animal animalToBeEdited = new Animal(dto);

        when(this.animalRepository.saveAndFlush(any())).thenReturn(animalToBeEdited);
        when(this.usuarioService.findUsuarioAuthority(any(), eq("TUTOR")))
                .thenReturn(
                        Optional.of(
                                Authority.builder()
                                        .id(1L)
                                        .permissao("TUTOR")
                                        .build()
                        )
                );

        AnimalRequestDto editionAnimal = factoryAnimalRequestDtoForEdition();

        animalToBeEdited = this.animalService.edit(editionAnimal, animalToBeEdited, List.of(getTutor()));

//        deixar o teste mais claro, nomear melhor as variaveis e colocar assertions melhores
        assertThat(animalToBeEdited.getPelagem()).isEqualTo("longa");
    }

    private AnimalRequestDto factoryAnimalRequestDto(){
        return new AnimalRequestDto(
                "Amy",
                1205,
                4.0f,
                "Pinscher",
                "canina",
                "curta",
                "FEMEA",
                List.of(1L)
                );
    }

    private AnimalRequestDto factoryAnimalRequestDtoForEdition(){
        return new AnimalRequestDto(
                "Amy",
                1205,
                5.0f,
                "Raça",
                "canina",
                "longa",
                "FEMEA",
                List.of(1L));
    }

    private Usuario getTutor(){
        Authority authority = Authority.builder()
                .id(1L)
                .permissao("TUTOR")
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
}

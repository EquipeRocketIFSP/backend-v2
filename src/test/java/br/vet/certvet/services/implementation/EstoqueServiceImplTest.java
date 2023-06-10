package br.vet.certvet.services.implementation;


import br.vet.certvet.repositories.EstoqueRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
class EstoqueServiceImplTest {

    private EstoqueRepository estoqueRepository = mock(EstoqueRepository.class);
    @InjectMocks
    private EstoqueServiceImpl estoqueService;

    @Test
    void createEstoque(){
        assertTrue(true);
    //when(estoqueRepository.save(any(Estoque.class))).
    }

}
  //  public void createCampus() {
//        when(campusRepository.save(any(Campus.class))).thenReturn(CampusFactoryUtils.sampleCampus());
//
//        Campus campusCreated = campusService.create(sampleCampusCreateDto(campus));
//
//        verify(campusRepository, times(1)).save(any(Campus.class));
//
//        assertThat(campusCreated).isNotNull();
//        assertThat(campusCreated.getId()).isNotNull();
//        assertThat(campusCreated.getName()).isEqualTo(campus.getName());
//        assertThat(campusCreated.getAbbreviation()).isEqualTo(campus.getAbbreviation());
//        assertThat(campusCreated.getAddress()).isEqualTo(campus.getAddress());
//        assertThat(campusCreated.getInternshipSector()).isEqualTo(campus.getInternshipSector());
//    }
//
package br.vet.certvet.services.implementation;


import br.vet.certvet.models.Estoque;
import br.vet.certvet.repositories.EstoqueRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EstoqueServiceImplTest {

    private EstoqueRepository estoqueRepository = mock(EstoqueRepository.class);
    @InjectMocks
    private EstoqueServiceImpl estoqueService;

    @Test
        public void createEstoque(){
        //when(estoqueRepository.save(any(Estoque.class))).
        }

        public void editEstoque(){}

        public void findOne(){}

        public void findAll(){}


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
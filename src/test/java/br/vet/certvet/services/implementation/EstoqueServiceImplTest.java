package br.vet.certvet.services.implementation;


import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.EstoqueTransacao;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.EstoqueRepository;
import br.vet.certvet.repositories.EstoqueTransacaoRepository;
import br.vet.certvet.repositories.MedicamentoRespository;
import br.vet.certvet.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
public class EstoqueServiceImplTest {

    private EstoqueRepository estoqueRepository = mock(EstoqueRepository.class);


    private EstoqueTransacaoRepository estoqueTransacaoRepository = mock(EstoqueTransacaoRepository.class);


    private EstoqueServiceImpl estoqueService = new EstoqueServiceImpl(estoqueRepository, estoqueTransacaoRepository);



    @Test
        void createEstoque(){

        Usuario usuario = Usuario.builder().build();
        Medicamento medicamento = Medicamento.builder().build();
        EstoqueRequestDto estoqueRequestDto = new EstoqueRequestDto(new BigDecimal(1), "ml", "abc123", LocalDate.of(2023,05,29));
        Estoque estoque = Estoque.builder().build();
        Optional<Estoque> estoqueOptional = Optional.empty();
        EstoqueTransacao estoqueTransacao = EstoqueTransacao.builder().build();

        when(estoqueRepository.saveAndFlush(any())).thenReturn(estoque);
        when(estoqueRepository.findOneByMedicamentoAndLote(medicamento, estoqueRequestDto.lote())).thenReturn(estoqueOptional);
        when(estoqueTransacaoRepository.saveAndFlush(any())).thenReturn(estoqueTransacao);

        Estoque estoqueCriado = estoqueService.create(estoqueRequestDto, medicamento, usuario);

        assertEquals(estoqueCriado, estoque);
    }

        public void editEstoque(){}

        public void findOne(){}

        public void findAll(){}


}

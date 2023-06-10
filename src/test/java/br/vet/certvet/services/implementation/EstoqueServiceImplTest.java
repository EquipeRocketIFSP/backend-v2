package br.vet.certvet.services.implementation;


import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.dto.responses.EstoqueResponseDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.EstoqueTransacao;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.EstoqueRepository;
import br.vet.certvet.repositories.EstoqueTransacaoRepository;
import br.vet.certvet.repositories.MedicamentoRespository;
import br.vet.certvet.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
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
//    @Test
//    void editEstoque(){
//    EstoqueRequestDto dto = new EstoqueRequestDto();
//        dto.setLote("Lote1");
//        dto.setQuantidade(BigDecimal.valueOf(10));
//
//        Estoque estoque = Estoque.builder().build()();
//        estoque.setId(1L);
//        estoque.setMedicamento(new Medicamento());
//        estoque.setQuantidade(BigDecimal.valueOf(5));
//
//        Usuario responsavel = new Usuario();
//
//    Optional<Estoque> response = Optional.empty();
//    when(estoqueRepository.findOneByMedicamentoAndLote(estoque.getMedicamento(), dto.getLote()))
//            .thenReturn(response);
//
//    EstoqueTransacao transacao = new EstoqueTransacao();
//    when(estoqueTransacaoRepository.saveAndFlush(transacao)).thenReturn(transacao);
//
//    Estoque result = estoqueService.edit(dto, estoque, responsavel);
//
//        Assertions.assertEquals(dto.getLote(), result.getLote());
//        Assertions.assertEquals(dto.getQuantidade(), result.getQuantidade());
//}

        @Test
        void findOne(){

            Long id = 1L;
            Medicamento medicamento = Medicamento.builder().build();

            Estoque estoque = Estoque.builder().build();
            when(estoqueRepository.findOneByMedicamentoAndId(medicamento, id)).thenReturn(Optional.of(estoque));

            Estoque FindOne = estoqueService.findOne(id, medicamento);

        }

//        void findAll(){
//        int page = 1;
//        String url = "http:/medicamento";
//        Medicamento medicamento = Medicamento.builder().build();
//            Pageable pageable = PageRequest.of(page - 1, EstoqueServiceImpl.RESPONSE_LIMIT);
//
//            List<Estoque> estoques = new ArrayList<>(); // Crie uma lista de objetos Estoque válidos para testar o retorno
//            estoques.add(new Estoque());
//            estoques.add(new Estoque());
//            Page<Estoque> estoquePage = new PageImpl<>(estoques, pageable, estoques.size());
//
//            when(estoqueRepository.countByMedicamento(medicamento)).thenReturn((long) estoques.size());
//            when(estoqueRepository.findAllByMedicamentoOrderByIdDesc(pageable, medicamento)).thenReturn(estoquePage);
//
//
//            PaginatedResponse<EstoqueResponseDto> = estoqueService.findAll(page, url, medicamento);
//            Assertions.assertEquals(estoques.size().getMetadata().getTotal());
//            Assertions.assertEquals(estoques.size().getData().size());
//        }


}

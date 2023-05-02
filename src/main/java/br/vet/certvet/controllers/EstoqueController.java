package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.dto.responses.EstoqueResponseDto;
import br.vet.certvet.dto.responses.EstoqueTransacaoResponseDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.services.EstoqueService;
import br.vet.certvet.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping("/api/medicamento/{medicamento_id}/estoque")
public class EstoqueController {
    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("")
    public ResponseEntity<EstoqueResponseDto> create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid EstoqueRequestDto dto,
            @PathVariable("medicamento_id") Long medicamentoId
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Medicamento medicamento = this.medicamentoService.findOne(medicamentoId, clinica);
        Estoque estoque = this.estoqueService.create(dto, medicamento);

        return new ResponseEntity<>(new EstoqueResponseDto(estoque), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueResponseDto> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid EstoqueRequestDto dto,
            @PathVariable("medicamento_id") Long medicamentoId,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Medicamento medicamento = this.medicamentoService.findOne(medicamentoId, clinica);
        Estoque estoque = this.estoqueService.findOne(id, medicamento);
        estoque = this.estoqueService.edit(dto, estoque);

        return ResponseEntity.ok(new EstoqueResponseDto(estoque));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponseDto> findOne(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("medicamento_id") Long medicamentoId,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Medicamento medicamento = this.medicamentoService.findOne(medicamentoId, clinica);
        Estoque estoque = this.estoqueService.findOne(id, medicamento);

        return ResponseEntity.ok(new EstoqueResponseDto(estoque));
    }

    @GetMapping("")
    public ResponseEntity<PaginatedResponse<EstoqueResponseDto>> findAll(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("medicamento_id") Long medicamentoId,
            @RequestParam(name = "pagina", defaultValue = "1") int page,
            HttpServletRequest request
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Medicamento medicamento = this.medicamentoService.findOne(medicamentoId, clinica);
        PaginatedResponse<EstoqueResponseDto> response = this.estoqueService.findAll(page, request.getRequestURL().toString(), medicamento);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/transacoes")
    public ResponseEntity<List<EstoqueTransacaoResponseDto>> findAllTransactions(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("medicamento_id") Long medicamentoId,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Medicamento medicamento = this.medicamentoService.findOne(medicamentoId, clinica);
        Estoque estoque = this.estoqueService.findOne(id, medicamento);

        List<EstoqueTransacaoResponseDto> response = estoque.getTransacoes()
                .stream().map(EstoqueTransacaoResponseDto::new)
                .toList();

        return ResponseEntity.ok(response);
    }
}

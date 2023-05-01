package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.EstoqueRequestDto;
import br.vet.certvet.dto.responses.EstoqueResponseDto;
import br.vet.certvet.models.Estoque;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.services.EstoqueService;
import br.vet.certvet.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/medicamento/{medicamento_id}/estoque")
public class EstoqueController {
    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private EstoqueService estoqueService;

    @PostMapping("")
    public ResponseEntity<EstoqueResponseDto> create(
            @RequestBody @Valid EstoqueRequestDto dto,
            @PathVariable("medicamento_id") Long medicamentoId
    ) {
        Medicamento medicamento = this.medicamentoService.findOne(medicamentoId);
        Estoque estoque = this.estoqueService.create(dto, medicamento);

        return new ResponseEntity<>(new EstoqueResponseDto(estoque), HttpStatus.CREATED);
    }
}

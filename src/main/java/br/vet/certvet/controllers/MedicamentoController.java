package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.dto.responses.MedicamentoResponseDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/medicamento")
public class MedicamentoController extends BaseController {
    @Autowired
    private MedicamentoService medicamentoService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid MedicamentoRequestDto dto) {
        this.medicamentoService.create(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<PaginatedResponse<MedicamentoResponseDto>> findAll(
            @RequestParam(name = "pagina", defaultValue = "1") int page,
            @RequestParam(name = "buscar", defaultValue = "") String search,
            HttpServletRequest request
    ) {
        PaginatedResponse<MedicamentoResponseDto> response = this.medicamentoService.findAll(page, search, request.getRequestURL().toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDto> findOne(@PathVariable("id") Long id) {
        Medicamento medicamento = this.medicamentoService.findOne(id);

        return ResponseEntity.ok(new MedicamentoResponseDto(medicamento));
    }
}

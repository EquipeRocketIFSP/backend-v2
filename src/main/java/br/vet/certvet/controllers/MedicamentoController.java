package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.dto.responses.MedicamentoResponseDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping("/api/medicamento")
public class MedicamentoController extends BaseController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private MedicamentoService medicamentoService;

    @PostMapping
    public ResponseEntity<MedicamentoResponseDto> create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid MedicamentoRequestDto dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Medicamento medicamento = this.medicamentoService.create(dto, clinica);

        return new ResponseEntity<>(new MedicamentoResponseDto(medicamento), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<PaginatedResponse<MedicamentoResponseDto>> findAll(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestParam(name = "pagina", defaultValue = "1") int page,
            @RequestParam(name = "buscar", defaultValue = "") String search,
            HttpServletRequest request
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        PaginatedResponse<MedicamentoResponseDto> response = this.medicamentoService.findAll(page, search, request.getRequestURL().toString(), clinica);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDto> findOne(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Medicamento medicamento = this.medicamentoService.findOne(id, clinica);

        return ResponseEntity.ok(new MedicamentoResponseDto(medicamento));
    }
}

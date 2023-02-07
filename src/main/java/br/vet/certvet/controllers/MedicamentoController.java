package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.MedicamentoRequestDto;
import br.vet.certvet.dto.responses.MedicamentoResponseDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Medicamento;
import br.vet.certvet.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class MedicamentoController extends BaseController {
    @Autowired
    private MedicamentoService medicamentoService;

    @PostMapping("/medicamento")
    public ResponseEntity<MedicamentoResponseDto> create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid MedicamentoRequestDto dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Medicamento medicamento = this.medicamentoService.create(dto, clinica);

        return new ResponseEntity<>(new MedicamentoResponseDto(medicamento), HttpStatus.CREATED);
    }
}

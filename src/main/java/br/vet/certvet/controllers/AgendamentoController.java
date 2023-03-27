package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
import br.vet.certvet.dto.responses.AgendamentoResponseDto;
import br.vet.certvet.models.Agendamento;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AgendamentoController extends BaseController {
    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping("/agendamento")
    public ResponseEntity<AgendamentoResponseDto> create(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid AgendamentoRequestDto dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Agendamento agendamento = this.agendamentoService.create(dto, clinica);

        return new ResponseEntity<>(new AgendamentoResponseDto(agendamento), HttpStatus.CREATED);
    }

    @GetMapping("/agendamento")
    public ResponseEntity<List<AgendamentoResponseDto>> findAll(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestParam(name = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        List<Agendamento> agendamentos = this.agendamentoService.findAll(date, clinica);

        return ResponseEntity.ok(agendamentos.stream().map(AgendamentoResponseDto::factory).toList());
    }
}

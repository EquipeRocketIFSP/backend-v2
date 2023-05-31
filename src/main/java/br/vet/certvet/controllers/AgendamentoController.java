package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
import br.vet.certvet.dto.responses.AgendamentoCompleteResponseDto;
import br.vet.certvet.dto.responses.AgendamentoResponseDto;
import br.vet.certvet.models.Agendamento;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.services.AgendamentoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
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

    @PutMapping("/agendamento/{id}")
    public ResponseEntity<AgendamentoResponseDto> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid AgendamentoRequestDto dto,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Agendamento agendamento = this.agendamentoService.findOne(id, clinica);
        agendamento = this.agendamentoService.edit(dto, agendamento, clinica);

        return ResponseEntity.ok(new AgendamentoResponseDto(agendamento));
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

    @GetMapping("/agendamento/{id}")
    public ResponseEntity<AgendamentoCompleteResponseDto> findOne(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Agendamento agendamento = this.agendamentoService.findOne(id, clinica);

        return ResponseEntity.ok(new AgendamentoCompleteResponseDto(agendamento));
    }

    @DeleteMapping("/agendamento/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable("id") Long id
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Agendamento agendamento = this.agendamentoService.findOne(id, clinica);
        this.agendamentoService.delete(agendamento);

        return ResponseEntity.accepted().build();
    }
}

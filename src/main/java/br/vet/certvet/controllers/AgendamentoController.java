package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.AgendamentoRequestDto;
import br.vet.certvet.dto.responses.AgendamentoResponseDto;
import br.vet.certvet.models.Agendamento;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
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
}

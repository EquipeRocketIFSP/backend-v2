package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.dto.responses.ClinicaResponseDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/clinica")
public class ClinicaController extends BaseController {
    @Autowired
    private ClinicaService clinicaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ClinicaResponseDto> criar(
            @RequestBody @Valid ClinicaInicialRequestDto dto
    ) {
        Clinica clinica = this.clinicaService.criar(dto);
        this.usuarioService.criar(ClinicaController.getDonoDto(dto), clinica);
        this.usuarioService.criar(ClinicaController.getTecnicoDto(dto), clinica);

        return new ResponseEntity<>(new ClinicaResponseDto(clinica), HttpStatus.ACCEPTED);
    }

    private static FuncionarioRequestDto getDonoDto(ClinicaInicialRequestDto dto) {
        FuncionarioRequestDto usuarioDto = new FuncionarioRequestDto();

        usuarioDto.nome = dto.dono_nome;
        usuarioDto.email = dto.dono_email;
        usuarioDto.cpf = dto.dono_cpf;
        usuarioDto.rg = dto.dono_rg;
        usuarioDto.celular = dto.dono_celular;
        usuarioDto.telefone = dto.dono_telefone;
        usuarioDto.logradouro = dto.dono_logradouro;
        usuarioDto.numero = dto.dono_numero;
        usuarioDto.cep = dto.dono_cep;
        usuarioDto.bairro = dto.dono_bairro;
        usuarioDto.cidade = dto.dono_cidade;
        usuarioDto.estado = dto.dono_estado;
        usuarioDto.senha = dto.dono_senha;

        return usuarioDto;
    }

    private static VeterinarioRequestDto getTecnicoDto(ClinicaInicialRequestDto dto) {
        VeterinarioRequestDto usuarioDto = new VeterinarioRequestDto();

        usuarioDto.nome = dto.tecnico_nome;
        usuarioDto.email = dto.tecnico_email;
        usuarioDto.cpf = dto.tecnico_cpf;
        usuarioDto.rg = dto.tecnico_rg;
        usuarioDto.celular = dto.tecnico_celular;
        usuarioDto.telefone = dto.tecnico_telefone;
        usuarioDto.logradouro = dto.tecnico_logradouro;
        usuarioDto.numero = dto.tecnico_numero;
        usuarioDto.cep = dto.tecnico_cep;
        usuarioDto.bairro = dto.tecnico_bairro;
        usuarioDto.cidade = dto.tecnico_cidade;
        usuarioDto.estado = dto.tecnico_estado;
        usuarioDto.crmv = dto.tecnico_crmv;
        usuarioDto.senha = dto.tecnico_senha;

        return usuarioDto;
    }
}

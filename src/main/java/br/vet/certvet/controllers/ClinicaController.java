package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.ClinicaRequestDto;
import br.vet.certvet.dto.requests.ResponsavelTecnicoRequestDTO;
import br.vet.certvet.dto.responses.ClinicaResponseDto;
import br.vet.certvet.dto.responses.UsuarioResponseDto;
import br.vet.certvet.exceptions.ForbiddenException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ClinicaController extends BaseController {
    @Autowired
    private ClinicaService clinicaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/clinica")
    public ResponseEntity<ClinicaResponseDto> create(
            @RequestBody @Valid ClinicaInicialRequestDto dto
    ) {
        Clinica clinica = this.clinicaService.create(dto);
        return new ResponseEntity<>(new ClinicaResponseDto(clinica), HttpStatus.CREATED);
    }

    @PutMapping("/clinica")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<ClinicaResponseDto> edit(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid ClinicaRequestDto dto
    ) {
        Usuario usuario = this.tokenService.getUsuario(token);

        if (this.usuarioService.findUsuarioAuthority(usuario, "ADMIN").isEmpty())
            throw new ForbiddenException("Somente o administrador pode alterar os dados da clínica.");

        Clinica clinica = this.tokenService.getClinica(token);
        clinica = this.clinicaService.edit(dto, clinica);

        return ResponseEntity.ok(new ClinicaResponseDto(clinica));
    }

    @GetMapping("/clinica")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<ClinicaResponseDto> findOne(@RequestHeader(AUTHORIZATION) String token) {
        Clinica clinica = this.tokenService.getClinica(token);

        return ResponseEntity.ok(new ClinicaResponseDto(clinica));
    }

    @GetMapping("/clinica/responsavel-tecnico")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UsuarioResponseDto> findTechinialResponsible(@RequestHeader(AUTHORIZATION) String token) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario responsavelTecnico = clinica.getResponsavelTecnico();

        if (responsavelTecnico == null)
            throw new NotFoundException("Responsável técnico não definido");

        return ResponseEntity.ok(new UsuarioResponseDto(responsavelTecnico));
    }

    @PutMapping("/clinica/responsavel-tecnico")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UsuarioResponseDto> setTechinialResponsible(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestBody @Valid ResponsavelTecnicoRequestDTO dto
    ) {
        Clinica clinica = this.tokenService.getClinica(token);
        Usuario responsavelTecnico = this.tokenService.getUsuario(token);

        responsavelTecnico.setCrmv(dto.getCrmv());
        clinica.setResponsavelTecnico(responsavelTecnico);

        this.clinicaService.edit(clinica);

        return ResponseEntity.ok(new UsuarioResponseDto(responsavelTecnico));
    }
}

package br.vet.certvet.services;

import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.dto.responses.UsuarioResponseDto;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsuarioService {
    Usuario create(UsuarioRequestDto dto, Clinica clinica);

    Usuario create(FuncionarioRequestDto dto, Clinica clinica);

    Usuario create(VeterinarioRequestDto dto, Clinica clinica);

    Usuario edit(Usuario usuario);

    Usuario edit(UsuarioRequestDto dto, Usuario usuario);

    Usuario edit(FuncionarioRequestDto dto, Usuario usuario);

    Usuario edit(VeterinarioRequestDto dto, Usuario usuario);

    Usuario findOne(Long id, Clinica clinica);

    Usuario findOne(String username, Clinica clinica);

    PaginatedResponse<UsuarioResponseDto> findAll(int page, String url, Clinica clinica);

    void delete(Usuario usuario);

    Usuario recover(Usuario usuario);

    Optional<Authority> findUsuarioAuthority(Usuario usuario, String authority);

    List<Clinica> findClinicasFromUsuario(String email);
}

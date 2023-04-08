package br.vet.certvet.services;

import br.vet.certvet.dto.requests.*;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.dto.responses.UsuarioResponseDto;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioService {
    Usuario create(UsuarioRequestDto dto, Clinica clinica);

    Usuario create(FuncionarioRequestDto dto, Clinica clinica);

    Usuario create(VeterinarioRequestDto dto, Clinica clinica);

    Usuario edit(Usuario usuario);

    Usuario edit(UsuarioRequestDto dto, Usuario usuario);

    Usuario edit(FuncionarioEditRequestDto dto, Usuario usuario);

    Usuario edit(VeterinarioEditRequestDto dto, Usuario usuario);

    Usuario findOne(Long id, Clinica clinica);

    Usuario findOne(String username, Clinica clinica);

    Usuario findOne(String passwordResetToken);

    Usuario findOneVeterinario(Long id, Clinica clinica);

    PaginatedResponse<UsuarioResponseDto> findAll(int page, String search, String url, Clinica clinica);

    void delete(Usuario usuario);

    Usuario recover(Usuario usuario);

    Optional<Authority> findUsuarioAuthority(Usuario usuario, String authority);
}

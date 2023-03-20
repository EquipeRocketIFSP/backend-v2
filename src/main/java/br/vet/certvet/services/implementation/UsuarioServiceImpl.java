package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.*;
import br.vet.certvet.dto.responses.Metadata;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.dto.responses.UsuarioResponseDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.ForbiddenException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.AuthorityRepository;
import br.vet.certvet.repositories.UsuarioRepository;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private static final short RESPONSE_LIMIT = 30;

    @Override
    public Usuario create(UsuarioRequestDto dto, Clinica clinica) {
        Usuario usuario = new Usuario(dto, clinica);
        Authority authority = this.authorityRepository.findByAuthority("TUTOR");

        usuario.getAuthorities().add(authority);

        return this.save(usuario, clinica);
    }

    @Override
    public Usuario create(FuncionarioRequestDto dto, Clinica clinica) {
        Usuario usuario = new Usuario(dto, clinica);
        Authority authority = this.authorityRepository.findByAuthority("FUNCIONARIO");
        List<Authority> authoritiesUsuario = usuario.getAuthorities();

        authoritiesUsuario.add(authority);

        if (dto.is_admin) {
            authority = this.authorityRepository.findByAuthority("ADMIN");
            authoritiesUsuario.add(authority);
        }

        return this.save(usuario, clinica);
    }

    @Override
    public Usuario create(VeterinarioRequestDto dto, Clinica clinica) {
        Usuario usuario = new Usuario(dto, clinica);
        Authority authority = this.authorityRepository.findByAuthority("VETERINARIO");
        List<Authority> authoritiesUsuario = usuario.getAuthorities();

        authoritiesUsuario.add(authority);

        if (dto.is_admin) {
            authority = this.authorityRepository.findByAuthority("ADMIN");
            authoritiesUsuario.add(authority);
        }

        return this.save(usuario, clinica);
    }

    @Override
    public Usuario edit(Usuario usuario) {
        return this.usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario edit(UsuarioRequestDto dto, Usuario usuario) {
        usuario.fill(dto);

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario edit(FuncionarioEditRequestDto dto, Usuario usuario) {
        Optional<Authority> usuarioAuthority = UsuarioServiceImpl.getUsuarioAuthority(usuario, "FUNCIONARIO");

        if (usuarioAuthority.isEmpty())
            throw new ForbiddenException("Esse usuário não é um funcionário. Verifique a documentação da API.");

        usuario.fill(dto);

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario edit(VeterinarioEditRequestDto dto, Usuario usuario) {
        Optional<Authority> usuarioAuthority = UsuarioServiceImpl.getUsuarioAuthority(usuario, "VETERINARIO");

        if (usuarioAuthority.isEmpty())
            throw new ForbiddenException("Esse usuário não é um veterinário. Verifique a documentação da API.");

        usuario.fill(dto);

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario findOne(Long id, Clinica clinica) {
        Optional<Usuario> response = usuarioRepository.findByIdAndClinica(id, clinica);

        if (response.isEmpty())
            throw new NotFoundException("Usuário não encontrado");

        return response.get();
    }

    @Override
    public Usuario findOne(String username, Clinica clinica) {
        Optional<Usuario> response = this.usuarioRepository.findByUsernameAndClinica(username, clinica);

        if (response.isEmpty())
            throw new NotFoundException("Usuário não encontrado");

        return response.get();
    }

    @Override
    public Usuario findOne(String passwordResetToken) {
        Optional<Usuario> response = this.usuarioRepository.findByResetPasswordToken(passwordResetToken);

        if (response.isEmpty())
            throw new NotFoundException("Usuário não encontrado");

        return response.get();
    }

    @Override
    public PaginatedResponse<UsuarioResponseDto> findAll(int page, String search, String url, Clinica clinica) {
        page = Math.max(page, 1);

        String[] pathnames = url.split("/");
        String path = pathnames[pathnames.length - 1].toUpperCase();
        Authority authority = this.authorityRepository.findByAuthority(path);
        Long total = search.trim().isEmpty() ?
                this.usuarioRepository.countByAuthoritiesAndClinica(authority, clinica) :
                this.usuarioRepository.countByNomeContainingAndAuthoritiesAndClinica(search, authority, clinica);

        Pageable pageable = PageRequest.of(page - 1, UsuarioServiceImpl.RESPONSE_LIMIT);
        Metadata meta = new Metadata(url, page, UsuarioServiceImpl.RESPONSE_LIMIT, total);

        List<Usuario> usuarios = search.trim().isEmpty() ?
                this.usuarioRepository.findAllByAuthoritiesAndClinica(pageable, authority, clinica) :
                this.usuarioRepository.findAllByNomeContainingAndAuthoritiesAndClinica(pageable, search, authority, clinica);

        List<UsuarioResponseDto> usuariosResponseDtos = usuarios.stream()
                .map((usuario) -> new UsuarioResponseDto(usuario))
                .toList();

        return new PaginatedResponse<>(meta, usuariosResponseDtos);
    }

    @Override
    public void delete(Usuario usuario) {
        usuario.setDeletedAt(LocalDateTime.now());

        this.usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario recover(Usuario usuario) {
        usuario.setDeletedAt(null);

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Optional<Authority> findUsuarioAuthority(Usuario usuario, String authority) {
        return UsuarioServiceImpl.getUsuarioAuthority(usuario, authority);
    }

    private Usuario save(Usuario usuario, Clinica clinica) {
        Optional<Usuario> response = this.usuarioRepository.findByUsernameAndClinica(usuario.getUsername(), clinica);

        if (response.isPresent())
            throw new ConflictException("Usuário já existe.");

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    private static Optional<Authority> getUsuarioAuthority(Usuario usuario, String authorityName) {
        return usuario.getAuthorities()
                .stream()
                .filter((authority) -> authority.getAuthority().equals(authorityName))
                .findFirst();
    }
}

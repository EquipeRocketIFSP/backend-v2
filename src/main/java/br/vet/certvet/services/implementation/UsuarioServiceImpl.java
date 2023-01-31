package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
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

        usuario.getAuthorities().add(authority);

        return this.save(usuario, clinica);
    }

    @Override
    public Usuario create(VeterinarioRequestDto dto, Clinica clinica) {
        Usuario usuario = new Usuario(dto, clinica);
        Authority authority = this.authorityRepository.findByAuthority("VETERINARIO");

        usuario.getAuthorities().add(authority);

        return this.save(usuario, clinica);
    }

    @Override
    public Usuario edit(UsuarioRequestDto dto, Usuario usuario) {
        UsuarioServiceImpl.setDefaultUsuarioNewData(dto, usuario);

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario edit(FuncionarioRequestDto dto, Usuario usuario) {
        Optional<Authority> usuarioAuthority = UsuarioServiceImpl.getUsuarioAuthority(usuario, "FUNCIONARIO");

        if (usuarioAuthority.isEmpty())
            throw new ForbiddenException("Esse usuário não é um funcionário. Verifique a documentação da API.");

        UsuarioServiceImpl.setDefaultUsuarioNewData(dto, usuario);
        usuario.setPassword(dto.senha);

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario edit(VeterinarioRequestDto dto, Usuario usuario) {
        Optional<Authority> usuarioAuthority = UsuarioServiceImpl.getUsuarioAuthority(usuario, "VETERINARIO");

        if (usuarioAuthority.isEmpty())
            throw new ForbiddenException("Esse usuário não é um veterinário. Verifique a documentação da API.");

        UsuarioServiceImpl.setDefaultUsuarioNewData(dto, usuario);
        usuario.setPassword(dto.senha);
        usuario.setCrmv(dto.crmv);

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
    public PaginatedResponse<UsuarioResponseDto> findAll(int page, String url, Clinica clinica) {
        page = Math.max(page, 1);

        String[] pathnames = url.split("/");
        String path = pathnames[pathnames.length - 1].toUpperCase();
        Authority authority = this.authorityRepository.findByAuthority(path);
        Long total = this.usuarioRepository.countByAuthoritiesAndClinica(authority, clinica);

        Pageable pageable = PageRequest.of(page - 1, UsuarioServiceImpl.RESPONSE_LIMIT);
        Metadata meta = new Metadata(url, page, UsuarioServiceImpl.RESPONSE_LIMIT, total);

        List<UsuarioResponseDto> usuariosResponseDtos = this.usuarioRepository
                .findAllByAuthoritiesAndClinica(pageable, authority, clinica)
                .stream()
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

    private static void setDefaultUsuarioNewData(UsuarioRequestDto dto, Usuario usuario) {
        usuario.setNome(dto.nome);
        usuario.setUsername(dto.email);
        usuario.setRg(dto.rg);
        usuario.setCpf(dto.cpf);
        usuario.setCelular(dto.celular);
        usuario.setTelefone(dto.telefone);
        usuario.setLogradouro(dto.logradouro);
        usuario.setCep(dto.cep);
        usuario.setNumero(dto.numero);
        usuario.setBairro(dto.bairro);
        usuario.setCidade(dto.cidade);
        usuario.setEstado(dto.estado);
    }
}

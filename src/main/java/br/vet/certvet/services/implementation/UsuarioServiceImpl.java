package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.dto.responses.Metadata;
import br.vet.certvet.dto.responses.PaginatedResponse;
import br.vet.certvet.dto.responses.UsuarioResponseDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.FalhaEnvioEmailException;
import br.vet.certvet.exceptions.ForbiddenException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.AuthorityRepository;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.UsuarioRepository;
import br.vet.certvet.services.EmailService;
import br.vet.certvet.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {
    private static final String FUNCIONARIO = "FUNCIONARIO";
    private static final String VETERINARIO = "VETERINARIO";
    private static final String ADMIN = "ADMIN";
    private static final String USER_NOT_FOUND = "Usuário não encontrado";
    private final ClinicaRepository clinicaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthorityRepository authorityRepository;
    private final EmailService emailService;
    private static final short RESPONSE_LIMIT = 30;

    public UsuarioServiceImpl(
            ClinicaRepository clinicaRepository,
            UsuarioRepository usuarioRepository,
            AuthorityRepository authorityRepository,
            EmailService emailService
    ) {
        this.clinicaRepository = clinicaRepository;
        this.usuarioRepository = usuarioRepository;
        this.authorityRepository = authorityRepository;
        this.emailService = emailService;
    }

    @Override
    public Usuario initialResgistration(FuncionarioRequestDto dto, Clinica clinica) {
        Usuario usuario = new Usuario(dto, clinica);
        List<Authority> authoritiesUsuario = usuario.getAuthorities();

        authoritiesUsuario.add(this.authorityRepository.findByPermissao(FUNCIONARIO));

        if (dto instanceof VeterinarioRequestDto v && v.getCrmv() != null)
            authoritiesUsuario.add(this.authorityRepository.findByPermissao(VETERINARIO));

        if (dto.isAdmin())
            authoritiesUsuario.add(this.authorityRepository.findByPermissao(ADMIN));

        return this.save(usuario, clinica);
    }

    @Override
    public Usuario create(UsuarioRequestDto dto, Clinica clinica) {
        Usuario usuario = new Usuario(dto, clinica);
        Authority authority = authorityRepository.findByPermissao("TUTOR");
        usuario.getAuthorities().add(authority);

        return this.save(usuario, clinica);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Usuario create(FuncionarioRequestDto dto, Clinica clinica) {
        dto.setSenha(RandomString.make(16));

        Usuario usuario = new Usuario(dto, clinica);
        List<Authority> authoritiesUsuario = usuario.getAuthorities();

        authoritiesUsuario.add(this.authorityRepository.findByPermissao(FUNCIONARIO));

        if (dto instanceof VeterinarioRequestDto)
            authoritiesUsuario.add(this.authorityRepository.findByPermissao(VETERINARIO));

        if (dto.isAdmin())
            authoritiesUsuario.add(this.authorityRepository.findByPermissao(ADMIN));

        usuario = this.save(usuario, clinica);

        if (dto instanceof VeterinarioRequestDto v && v.isTechnicalResponsible())
            clinica.setResponsavelTecnico(usuario);

        this.clinicaRepository.saveAndFlush(clinica);

        StringBuilder message = new StringBuilder();
        message.append("<h1>Senha de acesso - CertVet</h1>")
                .append("<br/>").append("<p>Sua senha de acesso é: <b>").append(dto.getSenha()).append("</b></p>")
                .append("<b>Como medida de segurança, recomendamos fortemente que altere sua senha após o primeiro acesso.</b>");

        try {
            this.emailService.sendTextMessage(dto.getEmail(), "Senha de acesso - Certvet", message.toString());
        } catch (MessagingException e) {
            throw new FalhaEnvioEmailException(e.getCause());
        }

        return usuario;
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
    public Usuario edit(FuncionarioRequestDto dto, Usuario usuario) {
        Optional<Authority> usuarioAuthority = UsuarioServiceImpl.getUsuarioAuthority(usuario, "FUNCIONARIO");

        if (usuarioAuthority.isEmpty())
            throw new ForbiddenException("Esse usuário não é um funcionário. Verifique a documentação da API.");

        List<Authority> authoritiesUsuario = usuario.getAuthorities();
        authoritiesUsuario.clear();

        authoritiesUsuario.add(this.authorityRepository.findByPermissao(FUNCIONARIO));

        if (dto instanceof VeterinarioRequestDto)
            authoritiesUsuario.add(this.authorityRepository.findByPermissao(VETERINARIO));

        if (dto.isAdmin())
            authoritiesUsuario.add(this.authorityRepository.findByPermissao(ADMIN));

        usuario.fill(dto);

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario findOne(Long id, Clinica clinica) {
        Optional<Usuario> response = usuarioRepository.findByIdAndClinica(id, clinica);

        if (response.isEmpty())
            throw new NotFoundException(USER_NOT_FOUND);

        return response.get();
    }

    @Override
    public Usuario findOne(String username, Clinica clinica) {
        Optional<Usuario> response = this.usuarioRepository.findByUsernameAndClinica(username, clinica);

        if (response.isEmpty())
            throw new NotFoundException(USER_NOT_FOUND);

        return response.get();
    }

    @Override
    public Usuario findOne(String passwordResetToken) {
        Optional<Usuario> response = this.usuarioRepository.findByResetPasswordToken(passwordResetToken);

        if (response.isEmpty())
            throw new NotFoundException(USER_NOT_FOUND);

        return response.get();
    }

    @Override
    public Usuario findOneVeterinario(Long id, Clinica clinica) {
        final String NOT_FOUND_VETERINARIO = "Veterinário não encontrado";
        final String AUTHORITY_NAME = "VETERINARIO";

        Optional<Usuario> response = usuarioRepository.findByIdAndClinica(id, clinica);

        if (response.isEmpty())
            throw new NotFoundException(NOT_FOUND_VETERINARIO);

        Usuario usuario = response.get();

        if (this.findUsuarioAuthority(usuario, AUTHORITY_NAME).isEmpty())
            throw new NotFoundException(NOT_FOUND_VETERINARIO);

        return usuario;
    }

    @Override
    public PaginatedResponse<UsuarioResponseDto> findAll(int page, String search, String url, Clinica clinica) {
        page = Math.max(page, 1);

        String[] pathnames = url.split("/");
        String path = pathnames[pathnames.length - 1].toUpperCase();
        Authority authority = this.authorityRepository.findByPermissao(path);
        Long total = search.trim().isEmpty() ?
                this.usuarioRepository.countByAuthoritiesAndClinica(authority, clinica) :
                this.usuarioRepository.countByNomeContainingAndAuthoritiesAndClinica(search, authority, clinica);

        Pageable pageable = PageRequest.of(page - 1, UsuarioServiceImpl.RESPONSE_LIMIT);
        Metadata meta = new Metadata(url, page, UsuarioServiceImpl.RESPONSE_LIMIT, total);

        List<Usuario> usuarios = search.trim().isEmpty() ?
                this.usuarioRepository.findAllByAuthoritiesAndClinica(pageable, authority, clinica) :
                this.usuarioRepository.findAllByNomeContainingAndAuthoritiesAndClinica(pageable, search, authority, clinica);

        List<UsuarioResponseDto> usuariosResponseDtos = usuarios.stream()
                .map(UsuarioResponseDto::new)
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
        if (response.isPresent()) {
            log.info("usuario já existente");
            throw new ConflictException("Usuário já existe.");
        }
        log.info("nenhum registro anterior. cadastrando novo usuário");

        return this.usuarioRepository.saveAndFlush(usuario);
    }

    private static Optional<Authority> getUsuarioAuthority(Usuario usuario, String authorityName) {
        return usuario.getAuthorities()
                .stream()
                .filter(authority -> authority.getPermissao().equals(authorityName))
                .findFirst();
    }
}

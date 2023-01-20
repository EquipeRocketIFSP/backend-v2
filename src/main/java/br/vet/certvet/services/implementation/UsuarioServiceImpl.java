package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.models.Authority;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.AuthorityRepository;
import br.vet.certvet.repositories.UsuarioRepository;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Usuario criar(UsuarioRequestDto dto, Clinica clinica) {
        Usuario usuario = new Usuario(dto, clinica);
        Authority authority = this.authorityRepository.getByAuthority("TUTOR");

        usuario.getAuthorities().add(authority);

        return this.salvar(usuario, clinica);
    }

    @Override
    public Usuario criar(FuncionarioRequestDto dto, Clinica clinica) {
        Usuario usuario = new Usuario(dto, clinica);
        Authority authority = this.authorityRepository.getByAuthority("FUNCIONARIO");

        usuario.getAuthorities().add(authority);

        return this.salvar(usuario, clinica);
    }

    @Override
    public Usuario criar(VeterinarioRequestDto dto, Clinica clinica) {
        Usuario usuario = new Usuario(dto, clinica);
        Authority authority = this.authorityRepository.getByAuthority("VETERINARIO");

        usuario.getAuthorities().add(authority);

        return this.salvar(usuario, clinica);
    }

    @Override
    public Usuario find(Long id) {
        return usuarioRepository.getReferenceById(id);
    }

    private Usuario salvar(Usuario usuario, Clinica clinica) {
        Optional<Usuario> response = this.usuarioRepository.findByUsernameAndClinica(usuario.getUsername(), clinica);

        if (response.isPresent())
            throw new ConflictException("Usuário já existe.");

        return this.usuarioRepository.saveAndFlush(usuario);
    }
}

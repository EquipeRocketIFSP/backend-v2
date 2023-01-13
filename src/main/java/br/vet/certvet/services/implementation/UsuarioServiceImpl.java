package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.exceptions.ConfilctException;
import br.vet.certvet.model.Clinica;
import br.vet.certvet.model.Usuario;
import br.vet.certvet.repositories.UsuarioRepository;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario criar(UsuarioRequestDto dto, Clinica clinica) {
        Optional<Usuario> response = this.usuarioRepository.findByUsernameAndClinica(dto.email, clinica);

        if (response.isPresent())
            throw new ConfilctException("Usuário já existe.");

        return this.usuarioRepository.saveAndFlush(new Usuario(dto, clinica));
    }
}

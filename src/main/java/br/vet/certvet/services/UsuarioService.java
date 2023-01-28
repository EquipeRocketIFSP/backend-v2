package br.vet.certvet.services;

import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    Usuario create(UsuarioRequestDto dto, Clinica clinica);

    Usuario create(FuncionarioRequestDto dto, Clinica clinica);

    Usuario create(VeterinarioRequestDto dto, Clinica clinica);

    Usuario edit(UsuarioRequestDto dto, Usuario usuario);

    Usuario edit(FuncionarioRequestDto dto, Usuario usuario);

    Usuario edit(VeterinarioRequestDto dto, Usuario usuario);

    Usuario findById(Long id);

    Usuario findByUsername(String username, Clinica clinica);
}

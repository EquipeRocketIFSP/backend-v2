package br.vet.certvet.services;

import br.vet.certvet.dto.requests.UsuarioRequestDto;
import br.vet.certvet.model.Clinica;
import br.vet.certvet.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    Usuario criar(UsuarioRequestDto dto, Clinica clinica);
}

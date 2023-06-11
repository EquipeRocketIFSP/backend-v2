package br.vet.certvet.services;

import br.vet.certvet.dto.requests.CadastroTutorDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TutorService {
    Optional<Usuario> create(CadastroTutorDto tutorDto);

    Optional<Usuario> findByIdAndClinica(Long id, Clinica clinicaFromRequester);
}

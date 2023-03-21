package br.vet.certvet.services.implementation;

import br.vet.certvet.models.Prontuario;
import br.vet.certvet.services.ProntuarioService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProntuarioServiceImpl implements ProntuarioService {

    @Override
    public Optional<Prontuario> createProntuario(Prontuario prontuario) {
        return Optional.empty();
    }

    @Override
    public Optional<Prontuario> editProntuario(Prontuario prontuario) {
        return Optional.empty();
    }

    @Override
    public Optional<Prontuario> getProntuarioById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteProntuario(Prontuario prontuario) {
        return Boolean.FALSE;
    }

    @Override
    public byte[] getProntuarioPdfBy(Long id) {
        return new byte[0];
    }

    @Override
    public byte[] getProntuarioPdfBy(Prontuario prontuario) {
        return new byte[0];
    }
}

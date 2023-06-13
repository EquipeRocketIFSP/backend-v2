package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.CadastroTutorDto;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.TutorRepository;
import br.vet.certvet.services.TutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Slf4j
public class TutorServiceImpl implements TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;


    @Override
    public Optional<Usuario> create(CadastroTutorDto tutorDto) {
        Usuario tutor = convert(tutorDto);
        if (Boolean.TRUE.equals(tutorRepository.existsByCpfAndClinica(tutor.getCpf(), tutor.getClinica())))
            return Optional.empty();
        log.debug("Tutor cadastrado: " + tutor);
        return Optional.of(tutorRepository.save(tutor));
    }

    @Override
    public Optional<Usuario> findByIdAndClinica(Long id, Clinica clinicaFromRequester) {
        return tutorRepository.findByIdAndClinica(id, clinicaFromRequester);
    }

    private Usuario convert(CadastroTutorDto cadastroTutorDto) throws EntityNotFoundException {
        try {
            return Usuario.builder()
                    .nome(cadastroTutorDto.nome())
                    .cpf(cadastroTutorDto.cpf())
                    .rg(cadastroTutorDto.rg())
                    .cep(cadastroTutorDto.cep())
                    .logradouro(cadastroTutorDto.logradouro())
                    .numero(cadastroTutorDto.numero())
                    .bairro(cadastroTutorDto.bairro())
                    .cidade(cadastroTutorDto.cidade())
                    .estado(cadastroTutorDto.estado())
                    .celular(cadastroTutorDto.celular())
                    .email(cadastroTutorDto.email())
                    .telefone(cadastroTutorDto.telefone())
                    .clinica(
                            clinicaRepository.getReferenceById(cadastroTutorDto.clinica())
                    )
                    .build();
        } catch (EntityNotFoundException e){
            log.info(e.getLocalizedMessage());
            throw e;
        }
    }
}

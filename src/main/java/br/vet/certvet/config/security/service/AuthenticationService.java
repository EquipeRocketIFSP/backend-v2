package br.vet.certvet.config.security.service;

import br.vet.certvet.models.Clinica;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AuthenticationService implements UserDetailsService {
    final private static String NOT_AUTHORIZED = "Credenciais inválidas";

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAndClinicaId) throws UsernameNotFoundException {
        String[] strArray = emailAndClinicaId.split(" - ");

        if (strArray.length != 2)
            throw new UsernameNotFoundException(NOT_AUTHORIZED);

        String email = strArray[0];
        Optional<Clinica> response = this.clinicaRepository.findById(Long.parseLong(strArray[1]));

        if (response.isEmpty())
            throw new UsernameNotFoundException(NOT_AUTHORIZED);

        Clinica clinica = response.get();

        log.debug("User " + email + " trying to log on clinic " + clinica.getId());

        return repository.findByUsernameAndClinica(email, clinica)
                .orElseThrow(() -> new UsernameNotFoundException(NOT_AUTHORIZED));
    }
}

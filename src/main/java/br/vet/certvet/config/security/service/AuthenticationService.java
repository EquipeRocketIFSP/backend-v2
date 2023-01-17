package br.vet.certvet.config.security.service;

import br.vet.certvet.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AuthenticationService implements UserDetailsService {
    final private static String NOT_AUTHORIZED = "Credenciais inválidas";

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("User trying to log in: " + username);
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(NOT_AUTHORIZED));
    }


}

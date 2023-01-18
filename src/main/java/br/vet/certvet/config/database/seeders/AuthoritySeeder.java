package br.vet.certvet.config.database.seeders;

import br.vet.certvet.models.Authority;
import br.vet.certvet.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthoritySeeder implements CommandLineRunner {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        Authority[] authorities = {
                new Authority("ADMIN"),
                new Authority("VETERINARIO"),
                new Authority("FUNCIONARIO"),
                new Authority("TUTOR"),
        };

        this.authorityRepository.saveAllAndFlush(List.of(authorities));
    }
}

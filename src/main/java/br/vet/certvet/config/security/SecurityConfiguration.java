package br.vet.certvet.config.security;

import br.vet.certvet.config.security.filter.TokenAuthenticationFilter;
import br.vet.certvet.config.security.service.AuthenticationService;
import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.repositories.UsuarioRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@NoArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .requiresChannel().anyRequest().requiresSecure().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/error",
                        "/hello",
                        "/ping",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/actuator/**",
                        "/db/*",
                        "/db/**",
                        "/api/usuario/clinicas/*",
                        "/api/usuario/clinicas/**"
                ).permitAll()
                .antMatchers(HttpMethod.POST,
                        "/api/auth/*",
                        "/api/auth/**",
                        "/api/clinica/*",
                        "/api/clinica/**",
                        "/api/esqueci-minha-senha/*",
                        "/api/esqueci-minha-senha/**",
                        "/api/redefinir-senha/*",
                        "/api/redefinir-senha/**"
                ).permitAll()
                .anyRequest().authenticated()
//                .and().authorizeRequests().anyRequest().permitAll() // TODO: Comentar essa linha ao ativar SSL
                .and().cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new TokenAuthenticationFilter(tokenService, usuarioRepository, prontuarioRepository), UsernamePasswordAuthenticationFilter.class)
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}

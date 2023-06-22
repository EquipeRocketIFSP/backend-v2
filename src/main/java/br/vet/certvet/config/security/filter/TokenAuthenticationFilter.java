package br.vet.certvet.config.security.filter;


import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final String TYPE = "Bearer ";
    private static final String VALIDATION_EXCEPTION = "O codigo do prontuario não pôde ser validado";

    private TokenService tokenService;

    private UsuarioRepository repository;

    private ProntuarioRepository prontuarioRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith(TYPE)) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(TYPE.length());
        if(Boolean.TRUE.equals(tokenService.validate(token))){
            authenticate(token);
        }
        if(!request.getServletPath().contains("prescricao")) {
            filterChain.doFilter(request, response);
            return;
        }
        checkPrescricaoBelongsToClinica(request, token);
        filterChain.doFilter(request, response);
    }

    private void checkPrescricaoBelongsToClinica(HttpServletRequest request, String token) {
        String[] substr = request.getServletPath().split("/");
        String prontuarioCode = substr[substr.length-1];
        Prontuario prontuario = prontuarioRepository.findByCodigo(prontuarioCode)
                .orElseThrow(()->new ProntuarioNotFoundException(VALIDATION_EXCEPTION));
        if(!prontuario.getClinica().equals(tokenService.getUsuario(token).getClinica()))
            throw new ProntuarioNotFoundException(VALIDATION_EXCEPTION);
    }

    private void authenticate(String token) throws NoSuchElementException {
        Long userId = tokenService.getUsuarioId(token);
        Usuario usuario;
        try {
            usuario = repository.findById(userId).orElseThrow();
            log.info("Usuário logado: " + usuario.getUsername());
            if (usuario.getDeletedAt() != null)
                throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            log.error(e.getLocalizedMessage());
            throw new NoSuchElementException(e);
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

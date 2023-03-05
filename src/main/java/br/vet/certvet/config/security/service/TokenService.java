package br.vet.certvet.config.security.service;

import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.UsuarioService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TokenService {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClinicaService clinicaService;

    @Value("${app.jwt.expiration}")
    private String EXPIRATION;

    @Value("${app.jwt.secret}")
    private String SECRET;

    private final Date today = new Date();

    private static final int USUARIO_ID_INDEX = 0, CLINICA_ID_INDEX = 1;

    public String create(Authentication auth, Clinica clinica) {
        String subject = ((Usuario) auth.getPrincipal()).getId().toString() + ":" + clinica.getId().toString();

        return Jwts.builder()
                .setIssuer("CertVet")
                .setSubject(subject)
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + Long.parseLong(EXPIRATION)))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public Boolean validate(String token) {
        try {
            jwtDecode(token);
            log.debug("Token validado com sucesso!");
            return true;
        } catch (MalformedJwtException e) {
            log.error("MalformedJwtException: " + e.getLocalizedMessage());
        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException: " + e.getLocalizedMessage());
        } catch (UnsupportedJwtException e) {
            log.error("UnsupportedJwtException: " + e.getLocalizedMessage());
        } catch (SignatureException e) {
            log.error("SignatureException: " + e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException: " + e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("Exception: " + e.getLocalizedMessage());
        }
        log.info("Login sem sucesso");
        return false;
    }

    public Usuario getUsuario(String token) {
        Clinica clinica = this.getClinica(token);
        Long id = getUsuarioId(
                token.startsWith("Bearer ") ?
                        token.substring(7) :
                        token
        );

        return usuarioService.findOne(id, clinica);
    }

    public Long getUsuarioId(String token) {
        String[] ids = jwtDecode(token).getBody().getSubject().split(":");
        return Long.parseLong(ids[USUARIO_ID_INDEX]);
    }

    public Clinica getClinica(String token) {
        String[] ids = jwtDecode(token).getBody().getSubject().split(":");

        return this.clinicaService.findById(Long.parseLong(ids[CLINICA_ID_INDEX]));
    }

    private Jws<Claims> jwtDecode(String token) {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;

        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token);
    }
}

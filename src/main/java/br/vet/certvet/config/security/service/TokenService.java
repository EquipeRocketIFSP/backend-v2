package br.vet.certvet.config.security.service;

import br.vet.certvet.models.Usuario;
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
    @Value("${app.jwt.expiration}")
    private String EXPIRATION;
    @Value("${app.jwt.secret}")
    private String SECRET;
    private Date today = new Date();

    public String create(Authentication auth) {
        return Jwts.builder()
                .setIssuer("CertVet")
                .setSubject(((Usuario) auth.getPrincipal()).getId().toString())
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
        } catch (MalformedJwtException e){
            log.error("MalformedJwtException: " + e.getLocalizedMessage());
        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException: " + e.getLocalizedMessage());
        }catch (UnsupportedJwtException e){
            log.error("UnsupportedJwtException: " + e.getLocalizedMessage());
        }catch (SignatureException e) {
            log.error("SignatureException: " + e.getLocalizedMessage());
        }catch (IllegalArgumentException e){
            log.error("IllegalArgumentException: " + e.getLocalizedMessage());
        }catch (Exception e){
            log.error("Exception: " + e.getLocalizedMessage());
        }
        log.info("Login sem sucesso");
        return false;
    }

    private Jws<Claims> jwtDecode(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token);
    }

    public Long getUsuarioId(String token) {
        return Long.parseLong(
                jwtDecode(token)
                        .getBody()
                        .getSubject()
        );
    }

    public Usuario getUsuario(String token){
        return usuarioService.find(getUsuarioId(
                token.startsWith("Bearer ")
                        ? token.substring(7)
                        : token
        ));
    }
}

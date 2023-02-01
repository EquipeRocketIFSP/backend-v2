package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseController {

    @Autowired
    protected TokenService tokenService;

    protected Long getClinicaFromRequester(String auth) {
        return tokenService.getUsuario(auth).getClinica().getId();
    }


    protected void throwExceptionFromController(RuntimeException e) throws RuntimeException {
        log.error(e.getLocalizedMessage());
        throw e;
    }
}

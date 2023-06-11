package br.vet.certvet.controllers;

import br.vet.certvet.config.security.service.TokenService;
import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseController {

    @Autowired
    protected TokenService tokenService;


    @Autowired
    private ProntuarioService prontuarioService;


    protected Clinica getClinicaFromRequester(String auth) {
        return tokenService.getUsuario(auth).getClinica();
    }

    protected Long getClinicaIdFromRequester(String auth) {
        return getClinicaFromRequester(auth).getId();
    }

    protected Prontuario findProntuario(String prontuarioCodigo){
        return prontuarioService.findByCodigo(prontuarioCodigo)
                .stream()
                .findFirst()
                .orElseThrow(ProntuarioNotFoundException::new);
    }

    protected void throwExceptionFromController(RuntimeException e) throws RuntimeException {
        log.error(e.getLocalizedMessage());
        throw e;
    }
}

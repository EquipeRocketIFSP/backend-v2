package br.vet.certvet.exceptions;


public class FalhaEnvioEmailException extends RuntimeException {

    public FalhaEnvioEmailException(Throwable e){
        super(e);
    }
}

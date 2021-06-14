package br.gov.serpro.dedat.rescar.acesso.infrastructure.exception;

public class RescarException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RescarException(String msg) {
        super(msg);
    }
}
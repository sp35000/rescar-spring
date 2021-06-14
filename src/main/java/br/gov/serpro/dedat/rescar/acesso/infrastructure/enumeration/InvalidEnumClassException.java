package br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration;

public class InvalidEnumClassException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidEnumClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEnumClassException(String message) {
        super(message);
    }
}
package br.gov.serpro.dedat.rescar.acesso.infrastructure.validation;

import org.apache.commons.lang3.Validate;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.internacionalization.Mensagens;

public final class Validacao {

    private Validacao() {
    }

    public static void notNull(Object obj) {
        Validate.notNull(obj, Mensagens.get("nulo"));
    }

    public static void notBlank(String texto) {
        Validate.notBlank(texto, Mensagens.get("vazio"));
    }

    public static void size(String string, long min, long max) {
        Validate.inclusiveBetween(min, max, string.length(), Mensagens.get("tamanho", min, max));
    }

    public static void email(String email) {
        Validate.matchesPattern(email,
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            Mensagens.get("email"));
    }
}

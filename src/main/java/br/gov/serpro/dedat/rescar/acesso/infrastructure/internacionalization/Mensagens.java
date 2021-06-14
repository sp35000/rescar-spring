package br.gov.serpro.dedat.rescar.acesso.infrastructure.internacionalization;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public final class Mensagens {

    private static final ResourceBundle MSGS = ResourceBundle.getBundle("mensagens");

    private Mensagens() {
    }

    public static String get(String key) {
        return MSGS.getString(key);
    }

    public static String get(String key, Object... args) {
        return MessageFormat.format(get(key), args);
    }
}
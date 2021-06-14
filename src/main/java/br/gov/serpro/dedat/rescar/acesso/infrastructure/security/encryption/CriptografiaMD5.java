package br.gov.serpro.dedat.rescar.acesso.infrastructure.security.encryption;

import static java.util.logging.Logger.getLogger;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Criptografia;

@Component
public class CriptografiaMD5 implements Criptografia {

    private static final Logger LOG = getLogger(CriptografiaMD5.class.getName());

    @Override
    public String criptografar(String senha) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");

            BigInteger hash = new BigInteger(1, md.digest(senha.getBytes(Charset.defaultCharset())));
            return hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }
}
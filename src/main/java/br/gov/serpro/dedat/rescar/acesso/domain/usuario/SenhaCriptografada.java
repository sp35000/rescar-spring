package br.gov.serpro.dedat.rescar.acesso.domain.usuario;

import java.util.Objects;

import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Criptografia;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.Validacao;

public class SenhaCriptografada {

    private String texto;
    private Criptografia criptografia;

    public SenhaCriptografada(String texto, Criptografia criptografia) {
        Validacao.notBlank(texto);
        Validacao.size(texto, 6, 128);

        this.texto = texto;
        this.criptografia = criptografia;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.toString());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.toString(), other.toString());
    }

    @Override
    public String toString() {
        return this.criptografia.criptografar(this.texto);
    }
}
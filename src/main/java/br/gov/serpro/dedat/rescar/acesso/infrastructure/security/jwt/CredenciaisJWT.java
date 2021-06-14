package br.gov.serpro.dedat.rescar.acesso.infrastructure.security.jwt;

import java.util.Objects;
import java.util.UUID;

import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Credenciais;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.Validacao;

public class CredenciaisJWT extends Credenciais {

    private String token;

    public CredenciaisJWT(UUID id, String nome, String email, String perfil, String token) {
        super(id, nome, email, perfil);
        this.setToken(token);
    }

    private void setToken(String token) {
        Validacao.notNull(token);

        this.token = token;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.token);
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
        final Credenciais other = (Credenciais) obj;
        return Objects.equals(this.token, other.toString());
    }

    @Override
    public String toString() {
        return this.token;
    }
}
package br.gov.serpro.dedat.rescar.acesso.domain.seguranca;

import java.util.Objects;
import java.util.UUID;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.Validacao;

public class Credenciais {

    private UUID id;
    private String nome;
    private String email;
    private String perfil;

    public Credenciais(UUID id, String nome, String email, String perfil) {
        super();
        this.setId(id);
        this.setNome(nome);
        this.setEmail(email);
        this.setPerfil(perfil);
    }

    private void setId(UUID id) {
        Validacao.notNull(id);

        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }

    private void setNome(String nome) {
        Validacao.notNull(nome);

        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    private void setEmail(String email) {
        Validacao.notNull(email);

        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    private void setPerfil(String perfil) {
        Validacao.notNull(perfil);

        this.perfil = perfil;
    }

    public String getPerfil() {
        return this.perfil;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.email);
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
        return Objects.equals(this.id, other.id);
    }

    public boolean possuiPerfil(String perfil) {
        return this.perfil.equals(perfil);
    }
}
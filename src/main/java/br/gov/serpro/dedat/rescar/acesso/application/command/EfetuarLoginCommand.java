package br.gov.serpro.dedat.rescar.acesso.application.command;

import java.io.Serializable;

public class EfetuarLoginCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
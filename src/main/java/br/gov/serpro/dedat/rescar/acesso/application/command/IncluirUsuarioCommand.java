package br.gov.serpro.dedat.rescar.acesso.application.command;

import java.io.Serializable;

public class IncluirUsuarioCommand extends AbstractUsuarioCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private String perfil;

    public String getPerfil() {
        return this.perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
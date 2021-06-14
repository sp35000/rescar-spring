package br.gov.serpro.dedat.rescar.acesso.application.command;

import java.io.Serializable;
import java.util.UUID;

public class AlterarPerfilUsuarioCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String perfil;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPerfil() {
        return this.perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
package br.gov.serpro.dedat.rescar.acesso.application.command;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.beanvalidation.ValidEnum;

public class AlterarPerfilUsuarioCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private UUID id;
    
    @NotNull
    @ValidEnum(enumClass = Perfil.class)
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
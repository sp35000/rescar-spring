package br.gov.serpro.dedat.rescar.acesso.application.command;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.beanvalidation.ValidEnum;

public class IncluirUsuarioCommand extends AbstractUsuarioCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ValidEnum(enumClass = Perfil.class)
    private String perfil;

    public String getPerfil() {
        return this.perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
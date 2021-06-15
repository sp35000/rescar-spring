package br.gov.serpro.dedat.rescar.acesso.application.command;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.serpro.dedat.rescar.acesso.domain.usuario.SituacaoUsuario;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.beanvalidation.ValidEnum;

public class AlterarSituacaoUsuarioCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private UUID id;
    
    @NotNull
    @ValidEnum(enumClass = SituacaoUsuario.class)
    private String situacao;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSituacao() {
        return this.situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
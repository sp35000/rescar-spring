package br.gov.serpro.dedat.rescar.acesso.application.command;

import java.io.Serializable;
import java.util.UUID;

public class AlterarSituacaoUsuarioCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
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
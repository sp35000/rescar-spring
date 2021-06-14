package br.gov.serpro.dedat.rescar.acesso.application.command;

import java.io.Serializable;
import java.util.UUID;

public class AlterarDadosPessoaisCommand extends AbstractUsuarioCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
package br.gov.serpro.dedat.rescar.acesso.infrastructure.exception;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.internacionalization.Mensagens;

public class ObjetoNaoEncontradoException extends RescarBusinessException {

    private static final long serialVersionUID = 1L;

    public ObjetoNaoEncontradoException() {
        super(Mensagens.get("objeto-nao-encontrado"));
    }
}
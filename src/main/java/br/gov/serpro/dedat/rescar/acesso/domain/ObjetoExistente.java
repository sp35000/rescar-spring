package br.gov.serpro.dedat.rescar.acesso.domain;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.ObjetoNaoEncontradoException;

public class ObjetoExistente implements ValidationRule<Object> {

    @Override
    public void validate(Object obj) {
        if (obj == null) {
            throw new ObjetoNaoEncontradoException();
        }
    }
}
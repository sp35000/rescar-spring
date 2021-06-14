package br.gov.serpro.dedat.rescar.acesso.domain.usuario.validation;

import br.gov.serpro.dedat.rescar.acesso.domain.ValidationRule;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.repository.UsuarioRepository;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.RescarBusinessException;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.internacionalization.Mensagens;

public class UsuarioUnico implements ValidationRule<String> {

    private UsuarioRepository repository;

    public UsuarioUnico(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(String email) {
        if (this.repository.existsByEmail(email)) {
            throw new RescarBusinessException(Mensagens.get("usuario-ja-existe"));
        }
    }

}
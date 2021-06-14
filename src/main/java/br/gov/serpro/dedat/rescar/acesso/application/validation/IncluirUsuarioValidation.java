package br.gov.serpro.dedat.rescar.acesso.application.validation;

import br.gov.serpro.dedat.rescar.acesso.application.command.IncluirUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.domain.ValidationRule;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.repository.UsuarioRepository;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.validation.UsuarioUnico;

public class IncluirUsuarioValidation implements ValidationRule<IncluirUsuarioCommand> {

    private UsuarioRepository repository;

    public IncluirUsuarioValidation(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(IncluirUsuarioCommand command) {
        new UsuarioUnico(this.repository).validate(command.getEmail());
    }
}
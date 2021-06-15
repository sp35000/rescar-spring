package br.gov.serpro.dedat.rescar.acesso.application.validation;

import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarDadosPessoaisUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.domain.ObjetoExistente;
import br.gov.serpro.dedat.rescar.acesso.domain.ValidationRule;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Seguranca;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.repository.UsuarioRepository;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.validation.PermissaoAcessarDadosUsuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.validation.UsuarioUnico;

public class AlterarDadosPessoaisValidation implements ValidationRule<AlterarDadosPessoaisUsuarioCommand> {

    private UsuarioRepository repository;
    private Seguranca seguranca;
    private Usuario usuarioCadastrado;

    public AlterarDadosPessoaisValidation(UsuarioRepository repository, Seguranca seguranca, Usuario usuarioCadastrado) {
        this.repository = repository;
        this.seguranca = seguranca;
        this.usuarioCadastrado = usuarioCadastrado;
    }

    @Override
    public void validate(AlterarDadosPessoaisUsuarioCommand command) {
        new ObjetoExistente().validate(this.usuarioCadastrado);

        new PermissaoAcessarDadosUsuario(this.seguranca).validate(this.usuarioCadastrado);

        if (!this.usuarioCadastrado.getEmail().equals(command.getEmail())) {
            new UsuarioUnico(this.repository).validate(command.getEmail());
        }
    }
}
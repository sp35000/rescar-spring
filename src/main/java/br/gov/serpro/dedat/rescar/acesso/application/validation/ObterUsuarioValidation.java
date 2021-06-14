package br.gov.serpro.dedat.rescar.acesso.application.validation;

import br.gov.serpro.dedat.rescar.acesso.domain.ObjetoExistente;
import br.gov.serpro.dedat.rescar.acesso.domain.ValidationRule;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Seguranca;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.validation.PermissaoAcessarDadosUsuario;

public class ObterUsuarioValidation implements ValidationRule<Usuario> {

    private Seguranca seguranca;

    public ObterUsuarioValidation(Seguranca seguranca) {
        this.seguranca = seguranca;
    }

    @Override
    public void validate(Usuario usuarioCadastrado) {
        new ObjetoExistente().validate(usuarioCadastrado);

        new PermissaoAcessarDadosUsuario(this.seguranca).validate(usuarioCadastrado);
    }
}
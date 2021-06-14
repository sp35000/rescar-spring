package br.gov.serpro.dedat.rescar.acesso.domain.usuario.validation;

import br.gov.serpro.dedat.rescar.acesso.domain.ValidationRule;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Credenciais;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Seguranca;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.RescarBusinessException;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.internacionalization.Mensagens;

public class PermissaoAcessarDadosUsuario implements ValidationRule<Usuario> {

    private Seguranca seguranca;

    public PermissaoAcessarDadosUsuario(Seguranca seguranca) {
        this.seguranca = seguranca;
    }

    @Override
    public void validate(Usuario usuarioCadastrado) {
        Credenciais credenciais = this.seguranca.getCredenciais();

        if (!credenciais.possuiPerfil(Perfil.ADMINISTRADOR.getValue())
            && !credenciais.getId().equals(usuarioCadastrado.getId())) {
            throw new RescarBusinessException(Mensagens.get("usuario-sem-permissao-acesso-outro-usuario"));
        }
    }

}
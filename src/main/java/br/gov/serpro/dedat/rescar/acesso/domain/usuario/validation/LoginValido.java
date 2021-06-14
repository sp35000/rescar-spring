package br.gov.serpro.dedat.rescar.acesso.domain.usuario.validation;

import br.gov.serpro.dedat.rescar.acesso.domain.ValidationRule;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.SenhaCriptografada;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.RescarSecurityException;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.internacionalization.Mensagens;

public class LoginValido implements ValidationRule<SenhaCriptografada> {

    private Usuario usuarioCadastrado;

    public LoginValido(Usuario usuarioCadastrado) {
        this.usuarioCadastrado = usuarioCadastrado;
    }

    @Override
    public void validate(SenhaCriptografada senhaCriptografada) {
        if (this.usuarioCadastrado == null || !this.usuarioCadastrado.isSenhaValida(senhaCriptografada)) {
            throw new RescarSecurityException(Mensagens.get("usuario-ou-senha-invalida"));
        }
    }
}
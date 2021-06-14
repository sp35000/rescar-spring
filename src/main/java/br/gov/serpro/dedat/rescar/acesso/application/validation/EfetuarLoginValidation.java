package br.gov.serpro.dedat.rescar.acesso.application.validation;

import br.gov.serpro.dedat.rescar.acesso.application.command.EfetuarLoginCommand;
import br.gov.serpro.dedat.rescar.acesso.domain.ValidationRule;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Criptografia;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.SenhaCriptografada;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.validation.LoginValido;

public class EfetuarLoginValidation implements ValidationRule<EfetuarLoginCommand> {

    private Criptografia criptografia;
    private Usuario usuarioCadastrado;

    public EfetuarLoginValidation(Criptografia criptografia, Usuario usuarioCadastrado) {
        this.criptografia = criptografia;
        this.usuarioCadastrado = usuarioCadastrado;
    }

    @Override
    public void validate(EfetuarLoginCommand command) {
        new LoginValido(this.usuarioCadastrado).validate(new SenhaCriptografada(command.getSenha(), this.criptografia));
    }
}
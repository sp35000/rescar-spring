package br.gov.serpro.dedat.rescar.acesso.application;

import org.springframework.stereotype.Service;

import br.gov.serpro.dedat.rescar.acesso.application.command.EfetuarLoginCommand;
import br.gov.serpro.dedat.rescar.acesso.application.validation.EfetuarLoginValidation;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Credenciais;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Criptografia;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Seguranca;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.repository.UsuarioRepository;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.Validacao;

@Service
public class EfetuarLoginService {

    private UsuarioRepository usuarioRepository;
    private Criptografia criptografia;
    private Seguranca seguranca;

    public EfetuarLoginService(UsuarioRepository usuarioRepository, Criptografia criptografia, Seguranca seguranca) {
        this.usuarioRepository = usuarioRepository;
        this.criptografia = criptografia;
        this.seguranca = seguranca;
    }

    public Credenciais login(EfetuarLoginCommand command) {
        Validacao.notNull(command);

        Usuario usuarioCadastrado = this.usuarioRepository.getByEmail(command.getEmail());

        new EfetuarLoginValidation(this.criptografia, usuarioCadastrado).validate(command);

        return this.seguranca.criarCredenciais(usuarioCadastrado);
    }

    public Credenciais renovarCredenciais() {
        Credenciais credenciais = this.seguranca.getCredenciais();

        Usuario usuarioLogado = this.usuarioRepository.get(credenciais.getId());

        return this.seguranca.criarCredenciais(usuarioLogado);
    }
}
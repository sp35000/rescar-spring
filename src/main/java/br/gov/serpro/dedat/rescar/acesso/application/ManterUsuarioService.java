package br.gov.serpro.dedat.rescar.acesso.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarDadosPessoaisCommand;
import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarPerfilUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarSituacaoUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.command.IncluirUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.query.ListarUsuarioPorPerfilQuery;
import br.gov.serpro.dedat.rescar.acesso.application.representation.UsuarioRepresentation;
import br.gov.serpro.dedat.rescar.acesso.application.validation.AlterarDadosPessoaisValidation;
import br.gov.serpro.dedat.rescar.acesso.application.validation.IncluirUsuarioValidation;
import br.gov.serpro.dedat.rescar.acesso.application.validation.ObterUsuarioValidation;
import br.gov.serpro.dedat.rescar.acesso.domain.ObjetoExistente;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Criptografia;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Seguranca;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.SenhaCriptografada;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.SituacaoUsuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.repository.UsuarioRepository;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.Validacao;

public class ManterUsuarioService {

    private UsuarioRepository usuarioRepository;
    private Seguranca seguranca;
    private Criptografia criptografia;

    public ManterUsuarioService(UsuarioRepository usuarioRepository, Seguranca seguranca, Criptografia criptografia) {
        super();
        this.usuarioRepository = usuarioRepository;
        this.seguranca = seguranca;
        this.criptografia = criptografia;
    }

    public UsuarioRepresentation incluir(IncluirUsuarioCommand command) {
        Validacao.notNull(command);

        new IncluirUsuarioValidation(this.usuarioRepository).validate(command);

        Usuario usuario = new Usuario(command.getEmail(), new SenhaCriptografada(command.getSenha(), this.criptografia), command.getNome(),
            Perfil.toEnum(command.getPerfil()));

        this.usuarioRepository.add(usuario);

        return new UsuarioRepresentation(usuario);
    }

    public void alterarDadosPessoais(AlterarDadosPessoaisCommand command) {
        Validacao.notNull(command);

        Usuario usuarioCadastrado = this.usuarioRepository.get(command.getId());

        new AlterarDadosPessoaisValidation(this.usuarioRepository, this.seguranca, usuarioCadastrado).validate(command);

        usuarioCadastrado.alterarDadosPessoais(command.getEmail(), new SenhaCriptografada(command.getSenha(), this.criptografia), command.getNome());
    }

    public void alterarPerfil(AlterarPerfilUsuarioCommand command) {
        Validacao.notNull(command);

        Usuario usuarioCadastrado = this.usuarioRepository.get(command.getId());

        new ObjetoExistente().validate(usuarioCadastrado);

        usuarioCadastrado.alterarPerfil(Perfil.toEnum(command.getPerfil()));
    }

    public void alterarSituacao(AlterarSituacaoUsuarioCommand command) {
        Validacao.notNull(command);

        Usuario usuarioCadastrado = this.usuarioRepository.get(command.getId());

        new ObjetoExistente().validate(usuarioCadastrado);

        usuarioCadastrado.alterarSituacao(SituacaoUsuario.toEnum(command.getSituacao()));
    }

    public UsuarioRepresentation obter(UUID id) {
        Validacao.notNull(id);

        Usuario usuarioCadastrado = this.usuarioRepository.get(id);

        new ObterUsuarioValidation(this.seguranca).validate(usuarioCadastrado);

        return new UsuarioRepresentation(usuarioCadastrado);
    }

    public List<UsuarioRepresentation> listarPorPerfil(ListarUsuarioPorPerfilQuery query) {
        Validacao.notNull(query);

        List<UsuarioRepresentation> lista = new ArrayList<>();

        this.usuarioRepository.findByPerfil(Perfil.toEnum(query.getPerfil())).stream()
            .forEach(usuario -> lista.add(new UsuarioRepresentation(usuario)));

        return lista;
    }

    public List<UsuarioRepresentation> listar() {
        List<UsuarioRepresentation> lista = new ArrayList<>();

        this.usuarioRepository.all().stream().forEach(usuario -> lista.add(new UsuarioRepresentation(usuario)));

        return lista;
    }
}

package br.gov.serpro.dedat.rescar.acesso.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarDadosPessoaisCommand;
import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarPerfilUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarSituacaoUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.command.IncluirUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.representation.UsuarioRepresentation;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Credenciais;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Criptografia;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Seguranca;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.SenhaCriptografada;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.SituacaoUsuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.repository.UsuarioRepository;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.ObjetoNaoEncontradoException;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.RescarBusinessException;

public class ManterUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Criptografia criptografia;

    @Mock
    private Seguranca seguranca;

    @InjectMocks
    private ManterUsuarioService service;

    private Usuario administrador;

    private Usuario motorista;

    private List<Usuario> usuarios = new ArrayList<>();

    private Credenciais credenciaisAdministrador;

    private Credenciais credenciaisMotorista;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.administrador = new Usuario(
            "administrador@hotmail.com",
            new SenhaCriptografada("123456", this.criptografia),
            "Administrador Teste",
            Perfil.ADMINISTRADOR);

        Field idField = this.administrador.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(this.administrador, UUID.fromString("d0ff4b09-b179-4a54-a3b1-dc6090aface2"));

        this.motorista = new Usuario(
            "motorista@hotmail.com",
            new SenhaCriptografada("123456", this.criptografia),
            "Motorista da Silva",
            Perfil.MOTORISTA);

        idField.set(this.motorista, UUID.fromString("1c164984-24bb-4655-89d1-a39409d6fd80"));

        this.usuarios.add(this.administrador);
        this.usuarios.add(this.motorista);

        when(this.usuarioRepository.get(this.administrador.getId())).thenReturn(this.administrador);
        when(this.usuarioRepository.all()).thenReturn(this.usuarios);

        this.credenciaisAdministrador = new Credenciais(
            this.administrador.getId(),
            "Administrador Teste",
            "administrador@hotmail.com",
            Perfil.ADMINISTRADOR.getValue());

        this.credenciaisMotorista = new Credenciais(
            this.motorista.getId(),
            "Motorista da Silva",
            "motorista@hotmail.com",
            Perfil.MOTORISTA.getValue());

        when(this.seguranca.possuiPerfil(Perfil.ADMINISTRADOR.getValue())).thenReturn(true);
    }

    @Test
    public void inclusaoDeUsuario() {
        IncluirUsuarioCommand command = new IncluirUsuarioCommand();
        command.setEmail("wisni@hotmail.com");
        command.setSenha("123456");
        command.setNome("Rodrigo Francisco Wisnievski");
        command.setPerfil(Perfil.ADMINISTRADOR.getValue());

        UsuarioRepresentation representation = this.service.incluir(command);

        assertNotNull(representation);
        assertEquals("wisni@hotmail.com", representation.getEmail());
        assertEquals("Rodrigo Francisco Wisnievski", representation.getNome());
        assertEquals(Perfil.ADMINISTRADOR.getValue(), representation.getPerfil());
        assertEquals(SituacaoUsuario.ATIVO.getValue(), representation.getSituacao());
    }

    @Test
    public void inclusaoDeUsuarioJaExistente() {
        IncluirUsuarioCommand command = new IncluirUsuarioCommand();
        command.setEmail("wisni@hotmail.com");
        command.setSenha("123456");
        command.setNome("Rodrigo Francisco Wisnievski");
        command.setPerfil(Perfil.ADMINISTRADOR.getValue());

        when(this.usuarioRepository.existsByEmail("wisni@hotmail.com")).thenReturn(true);

        assertThrows(RescarBusinessException.class,
                () -> this.service.incluir(command),
                "Método 'incluir' deveria ter lançado exceção");
    }

    @Test
    public void alteracaoDeUsuario() {
        AlterarDadosPessoaisCommand command = new AlterarDadosPessoaisCommand();
        command.setId(this.administrador.getId());
        command.setEmail("alterado@hotmail.com");
        command.setSenha("1234567");
        command.setNome("Administrador Alterado da Silva");

        when(this.seguranca.getCredenciais()).thenReturn(this.credenciaisAdministrador);

        this.service.alterarDadosPessoais(command);

        assertEquals("alterado@hotmail.com", this.administrador.getEmail());
        assertEquals("Administrador Alterado da Silva", this.administrador.getNome());
    }

    @Test
    public void alteracaoDeUsuarioInexistente() {
        AlterarDadosPessoaisCommand command = new AlterarDadosPessoaisCommand();
        command.setId(this.administrador.getId());
        command.setEmail("alterado@hotmail.com");
        command.setSenha("1234567");
        command.setNome("Administrador Alterado da Silva");

        when(this.usuarioRepository.get(this.administrador.getId())).thenReturn(null);

        assertThrows(ObjetoNaoEncontradoException.class,
                () -> this.service.alterarDadosPessoais(command),
                "Método 'alterarDadosPessoais' deveria ter lançado exceção");
    }

    @Test
    public void alteracaoDeOutroUsuario() {
        AlterarDadosPessoaisCommand command = new AlterarDadosPessoaisCommand();
        command.setId(this.administrador.getId());
        command.setEmail("alterado@hotmail.com");
        command.setSenha("1234567");
        command.setNome("Administrador Alterado da Silva");

        when(this.seguranca.getCredenciais()).thenReturn(this.credenciaisMotorista);

        assertThrows(RescarBusinessException.class,
                () -> this.service.alterarDadosPessoais(command),
                "Método 'alterarDadosPessoais' deveria ter lançado exceção");
    }

    @Test
    public void alteracaoDePerfilDeUsuario() {
        AlterarPerfilUsuarioCommand command = new AlterarPerfilUsuarioCommand();
        command.setId(this.administrador.getId());
        command.setPerfil("M");

        this.service.alterarPerfil(command);

        assertEquals(Perfil.MOTORISTA, this.administrador.getPerfil());
    }

    @Test
    public void alteracaoDePerfilDeUsuarioInexistente() {
        AlterarPerfilUsuarioCommand command = new AlterarPerfilUsuarioCommand();
        command.setId(this.administrador.getId());

        when(this.usuarioRepository.get(this.administrador.getId())).thenReturn(null);

        assertThrows(ObjetoNaoEncontradoException.class,
                () -> this.service.alterarPerfil(command),
                "Método 'alterarPerfil' deveria ter lançado exceção");
    }

    @Test
    public void inativacaoDeUsuario() {
        AlterarSituacaoUsuarioCommand command = new AlterarSituacaoUsuarioCommand();
        command.setId(this.administrador.getId());
        command.setSituacao("I");

        this.service.alterarSituacao(command);

        assertEquals(SituacaoUsuario.INATIVO, this.administrador.getSituacao());
    }

    @Test
    public void inativacaoDeUsuarioInexistente() {
        AlterarSituacaoUsuarioCommand command = new AlterarSituacaoUsuarioCommand();
        command.setId(this.administrador.getId());

        when(this.usuarioRepository.get(this.administrador.getId())).thenReturn(null);

        assertThrows(ObjetoNaoEncontradoException.class,
                () -> this.service.alterarSituacao(command),
                "Método 'alterarSituacao' deveria ter lançado exceção");
    }

    @Test
    public void obtencaoDeUsuario() {
        when(this.seguranca.getCredenciais()).thenReturn(this.credenciaisAdministrador);

        UsuarioRepresentation representation = this.service.obter(this.administrador.getId());

        assertNotNull(representation);
    }

    @Test
    public void obtencaoDeUsuarioInexistente() {
        when(this.usuarioRepository.get(this.administrador.getId())).thenReturn(null);

        assertThrows(ObjetoNaoEncontradoException.class,
                () -> this.service.obter(this.administrador.getId()),
                "Método 'obter' deveria ter lançado exceção");
    }

    @Test
    public void listagemDeUsuario() {
        List<UsuarioRepresentation> representations = this.service.listar();

        assertNotNull(representations);
        assertTrue(representations.size() > 0);
    }
}
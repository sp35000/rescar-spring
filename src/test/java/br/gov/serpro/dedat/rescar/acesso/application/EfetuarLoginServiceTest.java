package br.gov.serpro.dedat.rescar.acesso.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.gov.serpro.dedat.rescar.acesso.application.command.EfetuarLoginCommand;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Credenciais;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Criptografia;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Seguranca;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.SenhaCriptografada;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.repository.UsuarioRepository;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.RescarSecurityException;

public class EfetuarLoginServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Seguranca seguranca;

    @Mock
    private Criptografia criptografia;

    @InjectMocks
    private EfetuarLoginService service;

    private Credenciais credenciais;

    private Usuario usuario;

    private UUID id = UUID.fromString("d0ff4b09-b179-4a54-a3b1-dc6090aface2");

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(this.criptografia.criptografar("123456")).thenReturn("e10adc3949ba59abbe56e057f20f883e");
        when(this.criptografia.criptografar("1234567")).thenReturn("fcea920f7412b5da7be0cf42b8c93759");

        this.usuario = new Usuario(
            "wisni@hotmail.com",
            new SenhaCriptografada("123456", this.criptografia),
            "Rodrigo Francisco Wisnievski",
            Perfil.ADMINISTRADOR);

        when(this.usuarioRepository.getByEmail("wisni@hotmail.com")).thenReturn(this.usuario);

        this.credenciais = new Credenciais(
            this.id,
            "Rodrigo Francisco Wisnievski",
            "wisni@hotmail.com",
            Perfil.ADMINISTRADOR.getValue());

        when(this.seguranca.getCredenciais()).thenReturn(this.credenciais);
        when(this.seguranca.criarCredenciais(this.usuario)).thenReturn(this.credenciais);
    }

    @Test
    public void login() {
        EfetuarLoginCommand command = new EfetuarLoginCommand();
        command.setEmail("wisni@hotmail.com");
        command.setSenha("123456");

        Credenciais credenciais = this.service.login(command);

        assertNotNull(credenciais);
    }

    @Test
    public void loginComCommandNull() {
        assertThrows(NullPointerException.class,
                () -> this.service.login(null),
                "Método 'login' deveria ter lançado exceção");
    }

    @Test
    public void loginComUsuarioInexistente() {
        EfetuarLoginCommand command = new EfetuarLoginCommand();
        command.setEmail("inexistente@hotmail.com");
        command.setSenha("123456");

        assertThrows(RescarSecurityException.class,
                () -> this.service.login(command),
                "Método 'login' deveria ter lançado exceção");
    }

    @Test
    public void loginComSenhaInvalida() {
        EfetuarLoginCommand command = new EfetuarLoginCommand();
        command.setEmail("wisni@hotmail.com");
        command.setSenha("1234567");

        assertThrows(RescarSecurityException.class,
                () -> this.service.login(command),
                "Método 'login' deveria ter lançado exceção");
    }
}
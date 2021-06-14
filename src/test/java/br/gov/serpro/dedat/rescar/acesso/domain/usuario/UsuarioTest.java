package br.gov.serpro.dedat.rescar.acesso.domain.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Criptografia;

public class UsuarioTest {

    @Mock
    private Criptografia criptografia;

    private Usuario usuario;

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
    }

    @Test
    public void criarUsuario() {
        Usuario usuarioNovo = new Usuario(
            "wisni@hotmail.com",
            new SenhaCriptografada("123456", this.criptografia),
            "Rodrigo Francisco Wisnievski",
            Perfil.ADMINISTRADOR);

        assertEquals("wisni@hotmail.com", usuarioNovo.getEmail());
        assertTrue(usuarioNovo.isSenhaValida(new SenhaCriptografada("123456", this.criptografia)));
        assertEquals("Rodrigo Francisco Wisnievski", usuarioNovo.getNome());
        assertEquals(Perfil.ADMINISTRADOR, usuarioNovo.getPerfil());
        assertEquals(SituacaoUsuario.ATIVO, usuarioNovo.getSituacao());
    }

    @Test
    public void senhaValida() {
        boolean retorno = this.usuario.isSenhaValida(new SenhaCriptografada("123456", this.criptografia));

        assertTrue(retorno);
    }

    @Test
    public void alterarDadosPessoais() {
        this.usuario.alterarDadosPessoais(
            "alterado@hotmail.com",
            new SenhaCriptografada("1234567", this.criptografia),
            "Alterado da Silva");

        assertEquals("alterado@hotmail.com", this.usuario.getEmail());
        assertTrue(this.usuario.isSenhaValida(new SenhaCriptografada("1234567", this.criptografia)));
        assertEquals("Alterado da Silva", this.usuario.getNome());
    }

    @Test
    public void alterarPerfil() {
        this.usuario.alterarPerfil(Perfil.MOTORISTA);

        assertEquals(Perfil.MOTORISTA, this.usuario.getPerfil());
    }

    @Test
    public void alterarSituacao() {
        this.usuario.alterarSituacao(SituacaoUsuario.INATIVO);

        assertEquals(SituacaoUsuario.INATIVO, this.usuario.getSituacao());
    }
}
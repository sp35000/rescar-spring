package br.gov.serpro.dedat.rescar.acesso.domain.seguranca;

import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;

public interface Seguranca {

    public Credenciais criarCredenciais(Usuario usuario);

    public Credenciais getCredenciais();

    public boolean possuiPerfil(String perfil);
}
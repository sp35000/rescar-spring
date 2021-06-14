package br.gov.serpro.dedat.rescar.acesso.domain.usuario.repository;

import java.util.List;
import java.util.UUID;

import br.gov.serpro.dedat.rescar.acesso.domain.Repository;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;

public interface UsuarioRepository extends Repository<Usuario, UUID> {

    public Usuario getByEmail(String email);

    public boolean existsByEmail(String email);

    public List<Usuario> findByPerfil(Perfil perfil);
}
package br.gov.serpro.dedat.rescar.acesso.infrastructure.persistence.jpa.springdata;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;

@Repository
public interface SpringDataJPAUsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Transactional(readOnly = true)
    Usuario findByEmail(String email);

    @Transactional(readOnly = true)
    boolean existsByEmail(String email);

    @Transactional(readOnly = true)
    @Query("SELECT usuario FROM Usuario usuario WHERE usuario.perfil = :perfil")
    List<Usuario> findByPerfil(@Param("perfil") Perfil perfil);
}
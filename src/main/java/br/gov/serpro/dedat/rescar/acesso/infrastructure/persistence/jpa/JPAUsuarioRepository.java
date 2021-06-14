package br.gov.serpro.dedat.rescar.acesso.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.repository.UsuarioRepository;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.persistence.jpa.springdata.SpringDataJPAUsuarioRepository;

@Repository
public class JPAUsuarioRepository implements UsuarioRepository {

    private SpringDataJPAUsuarioRepository springRepository;

    public JPAUsuarioRepository(SpringDataJPAUsuarioRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public void add(Usuario obj) {
        this.springRepository.save(obj);
    }

    @Override
    public void remove(Usuario obj) {
        this.springRepository.delete(obj);

    }

    @Override
    public Usuario get(UUID id) {
        return this.springRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Usuario obj) {
        return this.springRepository.existsById(obj.getId());
    }

    @Override
    public List<Usuario> all() {
        return this.springRepository.findAll();
    }

    @Override
    public Usuario getByEmail(String email) {
        return this.springRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.springRepository.existsByEmail(email);
    }

    @Override
    public List<Usuario> findByPerfil(Perfil perfil) {
        return this.springRepository.findByPerfil(perfil);
    }
}
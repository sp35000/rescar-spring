package br.gov.serpro.dedat.rescar.acesso.application.representation;

import java.io.Serializable;
import java.util.UUID;

import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;

public class UsuarioRepresentation implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String email;
    private String nome;
    private String perfil;
    private String situacao;

    public UsuarioRepresentation(Usuario usuario) {
        super();
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nome = usuario.getNome();
        this.perfil = usuario.getPerfil().getValue();
        this.situacao = usuario.getSituacao().getValue();
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPerfil() {
        return this.perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getSituacao() {
        return this.situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
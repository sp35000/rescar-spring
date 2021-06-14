package br.gov.serpro.dedat.rescar.acesso.domain.usuario;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.persistence.jpa.converter.PerfilConverter;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.persistence.jpa.converter.SituacaoUsuarioConverter;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.Validacao;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Column(length = 128, nullable = false, unique = true)
	private String email;

	@Column(length = 128, nullable = false)
	private String senha;

	@Column(length = 128, nullable = false)
	private String nome;

	@Convert(converter = PerfilConverter.class)
	@Column(length = 1, nullable = false)
	private Perfil perfil;

	@Convert(converter = SituacaoUsuarioConverter.class)
	@Column(length = 1, nullable = false)
	private SituacaoUsuario situacao;

	private Usuario() {
		super();
		this.setSituacao(SituacaoUsuario.ATIVO);
	}

	public Usuario(String email, SenhaCriptografada senha, String nome, Perfil perfil) {
		this.setEmail(email);
		this.setSenha(senha);
		this.setNome(nome);
		this.setPerfil(perfil);
		this.setSituacao(SituacaoUsuario.ATIVO);
	}

	public UUID getId() {
		return this.id;
	}

	public String getEmail() {
		return this.email;
	}

	private void setEmail(String email) {
		Validacao.notBlank(email);
		Validacao.size(email, 5, 128);
		Validacao.email(email);

		this.email = email;
	}

	private void setSenha(SenhaCriptografada senha) {
		Validacao.notNull(senha);

		this.senha = senha.toString();
	}

	public String getNome() {
		return this.nome;
	}

	private void setNome(String nome) {
		Validacao.notBlank(nome);
		Validacao.size(nome, 3, 128);

		this.nome = nome;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	private void setPerfil(Perfil perfil) {
		Validacao.notNull(perfil);

		this.perfil = perfil;
	}

	public SituacaoUsuario getSituacao() {
		return this.situacao;
	}

	private void setSituacao(SituacaoUsuario situacao) {
		Validacao.notNull(situacao);

		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Usuario other = (Usuario) obj;
		return Objects.equals(this.id, other.id);
	}

	public boolean isSenhaValida(SenhaCriptografada senha) {
		Validacao.notNull(senha);

		return this.senha.equals(senha.toString());
	}

	public void alterarDadosPessoais(String email, SenhaCriptografada senha, String nome) {
		this.setEmail(email);
		this.setSenha(senha);
		this.setNome(nome);
	}

	public void alterarPerfil(Perfil perfil) {
		this.setPerfil(perfil);
	}

	public void alterarSituacao(SituacaoUsuario situacao) {
		this.setSituacao(situacao);
	}
}
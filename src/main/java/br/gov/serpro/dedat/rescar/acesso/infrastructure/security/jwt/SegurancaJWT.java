package br.gov.serpro.dedat.rescar.acesso.infrastructure.security.jwt;

import static java.util.logging.Logger.getLogger;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Credenciais;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Seguranca;
import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Usuario;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.RescarSecurityException;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.internacionalization.Mensagens;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.security.key.KeyUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SegurancaJWT implements Seguranca {

    private static final Logger LOG = getLogger(SegurancaJWT.class.getName());

    private String privateKey;
    private String publicKey;
    private Long expiration;

    public SegurancaJWT(
        @Value("${jwt.privateKey}") String privateKey,
        @Value("${jwt.publicKey}") String publicKey,
        @Value("${jwt.expiration}") Long expiration) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.expiration = expiration;
    }

    public CredenciaisJWT criarCredenciais(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(KeyUtil.getPublicKey(this.publicKey)).parseClaimsJws(token);

            return new CredenciaisJWT(
                UUID.fromString((String) claims.getBody().get("id")),
                (String) claims.getBody().get("nome"),
                (String) claims.getBody().get("email"),
                (String) claims.getBody().get("perfil"),
                token);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);

            throw new RescarSecurityException(e.getMessage());
        }
    }

    @Override
    public Credenciais criarCredenciais(Usuario usuario) {
        HashMap<String, Object> claims = new HashMap<>();

        claims.put("id", usuario.getId());
        claims.put("email", usuario.getEmail());
        claims.put("nome", usuario.getNome());
        claims.put("perfil", usuario.getPerfil().getValue());

        try {
            String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.RS256, KeyUtil.getPrivateKey(this.privateKey))
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .compact();

            return this.criarCredenciais(token);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);

            throw new RescarSecurityException(e.getMessage());
        }
    }

    @Override
    public Credenciais getCredenciais() {
        try {
            return (CredenciaisJWT) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new RescarSecurityException(Mensagens.get("usuario-nao-autenticado"));
        }
    }

    @Override
    public boolean possuiPerfil(String perfil) {
        Credenciais credenciais = this.getCredenciais();

        return credenciais != null && credenciais.possuiPerfil(perfil);
    }
}
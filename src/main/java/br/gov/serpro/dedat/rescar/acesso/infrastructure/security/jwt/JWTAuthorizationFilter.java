package br.gov.serpro.dedat.rescar.acesso.infrastructure.security.jwt;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private SegurancaJWT seguranca;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, SegurancaJWT seguranca) {
        super(authenticationManager);
        this.seguranca = seguranca;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.isEmpty(authorizationHeader)) {
            String token = authorizationHeader.substring("Bearer".length()).trim();

            CredenciaisJWT credenciais = this.seguranca.criarCredenciais(token);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                credenciais,
                null,
                Arrays.asList(new SimpleGrantedAuthority(credenciais.getPerfil())));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
}
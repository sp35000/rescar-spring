package br.gov.serpro.dedat.rescar.acesso.infrastructure.security;

import java.util.Arrays;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.gov.serpro.dedat.rescar.acesso.application.EfetuarLoginService;
import br.gov.serpro.dedat.rescar.acesso.application.command.EfetuarLoginCommand;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Credenciais;

@Component
public class RescarAuthenticationProvider implements AuthenticationProvider {

    private EfetuarLoginService service;

    public RescarAuthenticationProvider(EfetuarLoginService service) {
        this.service = service;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        EfetuarLoginCommand command = new EfetuarLoginCommand();

        try {
            command.setEmail(authentication.getName());
            command.setSenha(authentication.getCredentials().toString());

            Credenciais credenciais = this.service.login(command);

            return new UsernamePasswordAuthenticationToken(
                credenciais,
                command.getSenha(),
                Arrays.asList(new SimpleGrantedAuthority(credenciais.getPerfil())));
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
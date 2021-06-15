package br.gov.serpro.dedat.rescar.acesso.infrastructure.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.security.jwt.JWTAuthenticationFilter;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.security.jwt.JWTAuthorizationFilter;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.security.jwt.SegurancaJWT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private RescarAuthenticationProvider authProvider;
    private SegurancaJWT seguranca;

    private static final String[] PUBLIC_MATCHERS = {
        "/h2-console/**",
        "/swagger-ui.html**",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    };

    public SecurityConfig(RescarAuthenticationProvider authProvider, SegurancaJWT seguranca) {
        this.authProvider = authProvider;
        this.seguranca = seguranca;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http.cors().and().csrf().disable();

        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();

        http.addFilter(new JWTAuthenticationFilter(this.authenticationManager(), new RescarAuthenticationFailureHandler()));
        http.addFilter(new JWTAuthorizationFilter(this.authenticationManager(), this.seguranca));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
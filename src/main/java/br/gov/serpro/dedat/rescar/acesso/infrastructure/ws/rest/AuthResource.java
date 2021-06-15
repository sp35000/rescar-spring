package br.gov.serpro.dedat.rescar.acesso.infrastructure.ws.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.serpro.dedat.rescar.acesso.application.EfetuarLoginService;
import br.gov.serpro.dedat.rescar.acesso.domain.seguranca.Credenciais;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private EfetuarLoginService service;

    public AuthResource(EfetuarLoginService service) {
        this.service = service;
    }

    @GetMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        Credenciais credendicais = this.service.renovarCredenciais();

        response.addHeader("Authorization", "Bearer " + credendicais);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");

        return ResponseEntity.noContent().build();
    }
}
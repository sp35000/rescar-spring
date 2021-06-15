package br.gov.serpro.dedat.rescar.acesso.infrastructure.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.ws.rest.exceptionhandler.StandardError;

public class RescarAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
        Gson gson = new Gson();

        String json = gson.toJson(new StandardError(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), request.getRequestURI()));

        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().append(json);
    }
}
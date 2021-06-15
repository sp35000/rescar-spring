package br.gov.serpro.dedat.rescar.acesso.infrastructure.ws.rest.exceptionhandler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.ObjetoNaoEncontradoException;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.RescarBusinessException;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.exception.RescarSecurityException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjetoNaoEncontradoException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(RescarBusinessException.class)
    public ResponseEntity<StandardError> business(RescarBusinessException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.PRECONDITION_FAILED.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(err);
    }

    @ExceptionHandler(RescarSecurityException.class)
    public ResponseEntity<StandardError> security(RescarSecurityException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> security(AccessDeniedException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

        ValidationError err = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), request.getRequestURI());

        e.getBindingResult().getFieldErrors().stream().forEach(x -> err.addError(x.getField(), x.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }
}
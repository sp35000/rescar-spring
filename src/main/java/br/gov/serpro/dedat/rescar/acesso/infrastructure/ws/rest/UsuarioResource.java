package br.gov.serpro.dedat.rescar.acesso.infrastructure.ws.rest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.gov.serpro.dedat.rescar.acesso.application.ManterUsuarioService;
import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarDadosPessoaisUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarPerfilUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.command.AlterarSituacaoUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.command.IncluirUsuarioCommand;
import br.gov.serpro.dedat.rescar.acesso.application.query.ListarUsuarioPorPerfilQuery;
import br.gov.serpro.dedat.rescar.acesso.application.representation.UsuarioRepresentation;

@RestController
@RequestMapping(value = "/v1/usuarios")
public class UsuarioResource {

    private ManterUsuarioService service;

    public UsuarioResource(ManterUsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('A')")
    public ResponseEntity<Void> insert(@Valid @RequestBody IncluirUsuarioCommand command) {
        UsuarioRepresentation representation = this.service.incluir(command);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(representation.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('A')")
    public ResponseEntity<Void> update(@Valid @RequestBody AlterarDadosPessoaisUsuarioCommand command, @PathVariable UUID id) {
        command.setId(id);

        this.service.alterarDadosPessoais(command);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/perfil")
    @PreAuthorize("hasAnyRole('A')")
    public ResponseEntity<Void> updatePerfil(@Valid @RequestBody AlterarPerfilUsuarioCommand command, @PathVariable UUID id) {
        command.setId(id);

        this.service.alterarPerfil(command);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/situacao")
    @PreAuthorize("hasAnyRole('A')")
    public ResponseEntity<Void> updateSituacao(@Valid @RequestBody AlterarSituacaoUsuarioCommand command, @PathVariable UUID id) {
        command.setId(id);

        this.service.alterarSituacao(command);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioRepresentation> find(@PathVariable UUID id) {
        return ResponseEntity.ok().body(this.service.obter(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('A')")
    public ResponseEntity<List<UsuarioRepresentation>> findAll(@RequestParam(name = "perfil", required = false) String perfil) {
        List<UsuarioRepresentation> list;

        if (perfil != null) {
            ListarUsuarioPorPerfilQuery query = new ListarUsuarioPorPerfilQuery();
            query.setPerfil(perfil);

            list = this.service.listarPorPerfil(query);
        } else {
            list = this.service.listar();
        }

        return ResponseEntity.ok().body(list);
    }
}
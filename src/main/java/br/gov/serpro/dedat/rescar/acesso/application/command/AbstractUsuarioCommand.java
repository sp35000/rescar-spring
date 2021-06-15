package br.gov.serpro.dedat.rescar.acesso.application.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AbstractUsuarioCommand {

    @NotBlank
    @Email
    @Size(min = 5, max = 30)
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 128)
    private String senha;
    
    @NotBlank
    @Size(min = 3, max = 80)
    private String nome;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
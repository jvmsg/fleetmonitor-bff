package br.com.ddmx.fleetmonitor.bff.api.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RealizaLoginRequestWS {
    @NotNull(message = "Campo \"conta\" não pode ser nulo.")
    @NotEmpty(message = "Campo \"conta\" não ser vazio.")
    private String conta;
    @NotNull(message = "Campo \"login\" não pode ser nulo.")
    @NotEmpty(message = "Campo \"login\" não ser vazio.")
    private String login;
    @NotNull(message = "Campo \"senha\" não pode ser nulo.")
    @NotEmpty(message = "Campo \"senha\" não ser vazio.")
    private String senha;

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

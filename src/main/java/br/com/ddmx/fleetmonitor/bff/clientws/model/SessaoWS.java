package br.com.ddmx.fleetmonitor.bff.clientws.model;

import br.com.ddmx.careca.boot.clientws.model.base.BaseWS;

import javax.validation.constraints.NotNull;

public class SessaoWS extends BaseWS {
    @NotNull(message = "Campo conta deve ser preenchido.")
    private String conta;
    @NotNull(message = "Campo login deve ser preenchido.")
    private String login;
    @NotNull(message = "Campo senha deve ser preenchido.")
    private String senha;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

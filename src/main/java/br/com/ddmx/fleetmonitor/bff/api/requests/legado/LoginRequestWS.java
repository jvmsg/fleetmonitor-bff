package br.com.ddmx.fleetmonitor.bff.api.requests.legado;

import java.io.Serializable;

public class LoginRequestWS implements Serializable {
    private String conta;
    private String login;
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

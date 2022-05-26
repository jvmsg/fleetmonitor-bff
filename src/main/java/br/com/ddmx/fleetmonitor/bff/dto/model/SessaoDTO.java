package br.com.ddmx.fleetmonitor.bff.dto.model;

import br.com.ddmx.careca.boot.commons.model.dto.base.BaseDTO;

public class SessaoDTO extends BaseDTO {
    private String conta;
    private String login;
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

package br.com.ddmx.fleetmonitor.bff.clientws.filter;

import br.com.ddmx.careca.boot.clientws.filter.base.FilterBaseWS;

import java.util.List;

public class SessaoFilterWS extends FilterBaseWS {
    private List<String> tokens;
    private String conta;
    private String login;

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

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
}

package br.com.ddmx.fleetmonitor.bff.dto.filter;

import br.com.ddmx.careca.boot.commons.model.filters.base.FilterBase;

import java.util.List;

public class SessaoFilterDTO extends FilterBase {
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

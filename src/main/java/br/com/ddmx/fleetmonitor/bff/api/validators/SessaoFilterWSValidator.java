package br.com.ddmx.fleetmonitor.bff.api.validators;

import br.com.ddmx.careca.boot.api.validators.RequestValidator;
import br.com.ddmx.careca.boot.commons.utils.Util;
import br.com.ddmx.fleetmonitor.bff.clientws.filter.SessaoFilterWS;

import javax.ws.rs.core.MultivaluedMap;

public class SessaoFilterWSValidator extends RequestValidator<SessaoFilterWS> {
    @Override
    protected void validaRequest(SessaoFilterWS sessaoFilterWS, MultivaluedMap<String, String> multivaluedMap) {
        if (
                Util.isNullOrEmpty(sessaoFilterWS.getConta()) &&
                Util.isNullOrEmpty(sessaoFilterWS.getLogin()) &&
                Util.isNullOrEmpty(sessaoFilterWS.getTokens()) &&
                Util.isNullOrEmpty(sessaoFilterWS.getIds()) &&
                sessaoFilterWS.getLimit() == 0
        ){
            badRequestException("Pelo menos um dos campos [\"conta\", \"login\", \"tokens\", \"ids\", \"limit\"] deve estar preenchido.");
        }
    }
}

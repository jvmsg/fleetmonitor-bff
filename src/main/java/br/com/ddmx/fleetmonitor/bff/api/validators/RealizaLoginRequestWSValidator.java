package br.com.ddmx.fleetmonitor.bff.api.validators;

import br.com.ddmx.careca.boot.api.validators.RequestValidator;
import br.com.ddmx.careca.boot.commons.utils.Util;
import br.com.ddmx.fleetmonitor.bff.api.requests.RealizaLoginRequestWS;

import javax.ws.rs.core.MultivaluedMap;

public class RealizaLoginRequestWSValidator extends RequestValidator<RealizaLoginRequestWS> {
    @Override
    protected void validaRequest(RealizaLoginRequestWS realizaLoginRequestWS, MultivaluedMap<String, String> multivaluedMap) {
        if (Util.isNullOrEmpty(realizaLoginRequestWS.getConta()))
            badRequestException("O campo \"conta\" deve estar preenchido.");
        if (Util.isNullOrEmpty(realizaLoginRequestWS.getLogin()))
            badRequestException("O campo \"login\" deve estar preenchido.");
        if (Util.isNullOrEmpty(realizaLoginRequestWS.getSenha()))
            badRequestException("O campo \"senha\" deve estar preenchido.");
    }
}

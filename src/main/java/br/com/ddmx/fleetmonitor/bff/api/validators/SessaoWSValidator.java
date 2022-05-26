package br.com.ddmx.fleetmonitor.bff.api.validators;

import br.com.ddmx.careca.boot.api.validators.RequestValidator;
import br.com.ddmx.fleetmonitor.bff.clientws.model.SessaoWS;

import javax.ws.rs.core.MultivaluedMap;

public class SessaoWSValidator extends RequestValidator<SessaoWS> {
    @Override
    protected void validaRequest(SessaoWS sessaoWS, MultivaluedMap<String, String> multivaluedMap) {

    }
}

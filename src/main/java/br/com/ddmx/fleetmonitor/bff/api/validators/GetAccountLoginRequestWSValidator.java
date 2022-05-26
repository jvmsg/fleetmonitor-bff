package br.com.ddmx.fleetmonitor.bff.api.validators;

import br.com.ddmx.careca.boot.api.validators.RequestValidator;
import br.com.ddmx.fleetmonitor.bff.api.requests.legado.GetAccountLoginRequestWS;

import javax.ws.rs.core.MultivaluedMap;

public class GetAccountLoginRequestWSValidator extends RequestValidator<GetAccountLoginRequestWS> {
    @Override
    protected void validaRequest(GetAccountLoginRequestWS getAccountLoginRequestWS, MultivaluedMap<String, String> multivaluedMap) {

    }
}

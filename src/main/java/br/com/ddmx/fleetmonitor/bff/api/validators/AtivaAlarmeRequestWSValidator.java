package br.com.ddmx.fleetmonitor.bff.api.validators;

import br.com.ddmx.careca.boot.api.validators.RequestValidator;
import br.com.ddmx.fleetmonitor.bff.api.requests.legado.AtivaAlarmeRequestWS;

import javax.ws.rs.core.MultivaluedMap;

public class AtivaAlarmeRequestWSValidator extends RequestValidator<AtivaAlarmeRequestWS> {
    @Override
    protected void validaRequest(AtivaAlarmeRequestWS ativaAlarmeRequestWS, MultivaluedMap<String, String> multivaluedMap) {

    }
}

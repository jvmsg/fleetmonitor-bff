package br.com.ddmx.fleetmonitor.bff.api.validators;

import br.com.ddmx.careca.boot.api.validators.RequestValidator;
import br.com.ddmx.fleetmonitor.bff.api.requests.legado.AtualizaVeiculoRequestWS;

import javax.ws.rs.core.MultivaluedMap;

public class AtualizaVeiculoRequestWSValidator extends RequestValidator<AtualizaVeiculoRequestWS> {
    @Override
    protected void validaRequest(AtualizaVeiculoRequestWS atualizaVeiculoRequestWS, MultivaluedMap<String, String> multivaluedMap) {

    }
}

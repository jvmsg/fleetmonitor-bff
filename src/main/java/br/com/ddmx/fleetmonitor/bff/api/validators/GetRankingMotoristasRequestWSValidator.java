package br.com.ddmx.fleetmonitor.bff.api.validators;

import br.com.ddmx.careca.boot.api.validators.RequestValidator;
import br.com.ddmx.fleetmonitor.bff.api.requests.legado.GetRankingMotoristasRequestWS;

import javax.ws.rs.core.MultivaluedMap;

public class GetRankingMotoristasRequestWSValidator extends RequestValidator<GetRankingMotoristasRequestWS> {
    @Override
    protected void validaRequest(GetRankingMotoristasRequestWS getRankingMotoristasRequestWS, MultivaluedMap<String, String> multivaluedMap) {

    }
}
